#include "stdafx.h"
#include "TransferClient.h"

#include <fstream>
#include <filesystem>

namespace fs = std::filesystem;
namespace tf
{
    TransferClient::TransferClient(asio::io_context& io_context,
        const tcp::resolver::results_type& endpoints)
        : io_context(io_context),
        socket(io_context),
        buf(),
        state(client::ClientState::Waiting),
        pfile(),
        fileLength(0),
        fileLengthSended(false),
        bufMaxSize(1024)
    {
        doConnect(endpoints);
        buf.resize(bufMaxSize);
    }

    TransferClient::~TransferClient()
    {
        close();
    }

    // 传输文件
    int TransferClient::sendFile(string filePath)
    {
        if (!pCtx)
            return -1;

        if (state == client::ClientState::Done
            || state == client::ClientState::Waiting)
        {

            uintmax_t size = fs::file_size(filePath);
            uintmax_t maxSupportSize = 4294967296; // 这里乘法居然不起作用！！ 4 * 1024 * 1024 * 1024
            if (size > maxSupportSize)
            {
                // 为了简单不支持超过 4G 的文件传输
                return -1;
            }

            pCtx->setState(tf::State::Running);

            currentFilePath = filePath;
            pfile = make_shared<ifstream>(currentFilePath, std::ios::binary);
            if (!pfile->is_open())
            {
                OutputDebugStringW(L"file open failed");
                return -1;
            }

            state = client::ClientState::SendingLength;
            bufWriteSize = 4;
            fileLength = size;
            fileLengthSended = false;
            remain = fileLength;
            doSend();
            return 0;
        }
        else
        {
            return -1;
        }
    }

    void TransferClient::doSend()
    {
        // TODO: 准备 sendlength and send data
        if (state == client::ClientState::SendingLength)
        {
            for (size_t i = 0; i < bufWriteSize; i++)
            {
                buf[i] = fileLength >> ((3 - i) * 8);
            }
            asio::async_write(socket, asio::buffer(buf, bufWriteSize),
                [this](std::error_code ec, std::size_t /*length*/)
            {
                if (!ec)
                {
                    state = client::ClientState::SendingData;
                    if (remain < bufMaxSize)
                    {
                        bufWriteSize = remain;
                    }
                    else
                    {
                        bufWriteSize = bufMaxSize;
                    }
                    doSend();
                }
                else
                {
                    socket.close();
                }
            });
        }
        else if (state == client::ClientState::SendingData)
        {
            size_t sended = fileLength - remain;
            pfile->read(reinterpret_cast<char*>(&buf[0]), bufWriteSize);

            pCtx->setPercent(sended / fileLength);

            asio::async_write(socket, asio::buffer(buf, bufWriteSize),
                [this](std::error_code ec, std::size_t length)
            {
                if (!ec)
                {
                    remain -= length;
                    if (remain <= 0)
                    {
                        state = client::ClientState::Done;

                        pCtx->setState(tf::State::Done);
                        fileLengthSended = true;
                    }
                    else
                    {
                        if (remain < bufMaxSize)
                            bufWriteSize = remain;
                        else
                            bufWriteSize = bufMaxSize;
                        doSend();
                    }
                }
                else
                {
                    socket.close();
                }
            });
        }
        // else ignore
    }

    void TransferClient::close()
    {
        asio::post(io_context, [this]() { socket.close(); });
    }

    void TransferClient::setContext(shared_ptr<TransferContext> pCtx)
    {
        this->pCtx = pCtx;
    }

    void TransferClient::doConnect(const tcp::resolver::results_type & endpoints)
    {
        asio::async_connect(socket, endpoints,
            [this](std::error_code ec, tcp::endpoint)
        {
            if (!ec)
            {
                OutputDebugStringW(L"client connected");
            }
        });
    }

}

