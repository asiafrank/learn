#include "pch.h"
#include "Session.h"

#include <fstream>

using namespace std;
using asio::ip::tcp;

Session::Session(tcp::socket socket)
    : socket(std::move(socket)),
    bufMaxSize(1024),
    bufReadSize(4),
    buf(),
    state(State::ReceiveLength),
    dataLength(0),
    dataRemain(0),
    pfile()
{
    // buf ��С 1KB
    buf.resize(bufMaxSize);
}

Session::~Session()
{
    cout << "session released" << endl;
}

void Session::start()
{
    doRead();
}

void Session::doRead()
{
    auto self(shared_from_this());

    if (state == State::ReceiveLength)
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
                    for (size_t i = 3; it != end && i >= 0; i++)
                    {
                        dataLength = dataLength | (*it) << (3 * 8);
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

                    state = State::ReceiveData;
                    cout << "dataLength�� " << dataLength << endl;
                    doRead();
                }
            }
        );
    }
    else if (state == State::ReceiveData)
    {
        asio::async_read(socket, asio::buffer(buf, bufReadSize),
            [this, self](asio::error_code ec, size_t length)
            {
                if (!ec)
                {
                    // ����������д���ļ���
                    pfile->write(reinterpret_cast<char*>(&buf[0]), length);
                    dataRemain -= length;

                    // ������һ�ζ�ȡ�� bufReadSize
                    if (dataRemain <= 0) // ��ʾ�Ѿ���ȡ���
                    {
                        // ׼����һ�ζ�ȡ�ļ�
                        state = State::ReceiveLength;
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