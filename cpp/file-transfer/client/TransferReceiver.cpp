#include "stdafx.h"
#include "TransferReceiver.h"

using namespace std;
using asio::ip::tcp;

namespace tf
{
    TransferReceiver::TransferReceiver(tcp::socket socket)
        : socket(std::move(socket)),
        dataLength(0),
        dataRemain(0),
        bufMaxSize(1024),
        bufReadSize(4),
        buf(),
        state(receiver::GettingLength),
        tmpFileName("test.data"),
        pfile()
    {
        // buf ��С 1KB
        buf.resize(bufMaxSize);
    }

    TransferReceiver::~TransferReceiver()
    {
        globalLog()->info("receiver released");
    }

    void TransferReceiver::start()
    {
        doRead();
    }

    const receiver::State & TransferReceiver::getState() const
    {
        return state;
    }

    void TransferReceiver::doRead()
    {
        auto self(shared_from_this());

        if (state == receiver::GettingLength)
        {
            asio::async_read(socket, asio::buffer(buf, bufReadSize),
                [this, self](asio::error_code ec, size_t length)
            {
                if (!ec)
                {
                    // �������ݳ���
                    auto it = buf.cbegin();
                    auto end = buf.cend();
                    // big-endian
                    dataLength = 0;
                    for (int i = 3; it != end && i >= 0; i--, it++)
                    {
                        uint32_t tmp = (uint32_t)(*it);
                        tmp = tmp << (i * 8);
                        dataLength = dataLength | tmp;
                    }
                    dataRemain = dataLength;

                    // TODO: �½��ļ������úô�С��ʹ���ڴ�ӳ���ļ�
                    std::remove(tmpFileName.c_str());
                    pfile = make_shared<ofstream>(tmpFileName, std::ios::binary);

                    // ������һ�ζ�ȡ�� bufReadSize
                    if (dataRemain < bufMaxSize)
                        bufReadSize = dataRemain;
                    else
                        bufReadSize = bufMaxSize;

                    state = receiver::GettingData;
                    cout << "dataLength�� " << dataLength << endl;
                    doRead();
                }
            }
            );
        }
        else if (state == receiver::GettingData)
        {
            cout << "wait to receivedata" << endl;
            asio::async_read(socket, asio::buffer(buf, bufReadSize),
                [this, self](asio::error_code ec, size_t length)
            {
                if (!ec)
                {
                    cout << "receivedata success: " << length << endl;
                    // ����������д���ļ���
                    pfile->write(reinterpret_cast<char*>(&buf[0]), length);
                    dataRemain -= (uint32_t)length;

                    // ������һ�ζ�ȡ�� bufReadSize
                    if (dataRemain <= 0) // ��ʾ�Ѿ���ȡ���
                    {
                        // ׼����һ�ζ�ȡ�ļ�
                        state = receiver::GettingLength;
                        // dont clear buf
                        bufReadSize = 4;
                        pfile = nullptr;
                    }
                    else
                    {
                        if (dataRemain < bufMaxSize)
                            bufReadSize = dataRemain;
                        else
                            bufReadSize = bufMaxSize;

                        doRead();
                    }
                }
            }
            );
        }
        // else ignore
    }
} // namespace tf end
