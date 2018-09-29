#include "stdafx.h"
#include "TransferSender.h"

#include <fstream>
#include <filesystem>

namespace fs = std::filesystem;
namespace tf
{
    TransferSender::TransferSender(asio::io_context& io_context,
        const tcp::resolver::results_type& endpoints)
        : io_context(io_context),
        socket(io_context),
        buf(),
        state(sender::Waiting),
        pfile(),
        fileLength(0),
        fileLengthSended(false),
        bufMaxSize(1024)
    {
        doConnect(endpoints);
        buf.resize(bufMaxSize);
    }

    TransferSender::~TransferSender()
    {
        close();
    }

    // 传输文件
    int TransferSender::sendFile(string filePath)
    {
        if (!pCtx)
            return -1;

        if (state == sender::Done
            || state == sender::Waiting)
        {

            uintmax_t size = fs::file_size(filePath);
            uintmax_t maxSupportSize = 4294967296; // 这里乘法居然不起作用！！ 4 * 1024 * 1024 * 1024 Byte = 4GB
            if (size > maxSupportSize)
            {
                // 为了简单不支持超过 4G 的文件传输
                return -1;
            }

            pCtx->setState(tf::context::Running);

            currentFilePath = filePath;
            pfile = make_shared<ifstream>(currentFilePath, std::ios::binary);
            if (!pfile->is_open())
            {
                globalLog()->info("file {} open failed", currentFilePath);
                return -1;
            }

            state = sender::SendingLength;
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

    void TransferSender::doSend()
    {
        globalLog()->info("=============== doSend ====================");
        if (state == sender::SendingLength)
        {
            int len = (int)fileLength;
            for (int i = 0; i < bufWriteSize; i++)
            {
                int tmp = (len >> ((3 - i) * 8)) & 0x000000FF;
                buf[i] = (Byte)tmp;
            }
            asio::async_write(socket, asio::buffer(buf, bufWriteSize),
                [this](std::error_code ec, std::size_t length)
            {
                if (!ec)
                {
                    globalLog()->info("length send succcess");
                    state = sender::SendingData;
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
                    close();
                }
            });
        }
        else if (state == sender::SendingData)
        {
            size_t sended = fileLength - remain;
            pfile->read(reinterpret_cast<char*>(&buf[0]), bufWriteSize);

            globalLog()->info("sending data invoke");
            float percent = (float)sended / fileLength;
            pCtx->setPercent(percent);

            asio::async_write(socket, asio::buffer(buf, bufWriteSize),
                [this](std::error_code ec, std::size_t length)
            {
                if (!ec)
                {
                    globalLog()->info("sending data success");
                    remain -= length;
                    if (remain <= 0)
                    {
                        state = sender::Done;

                        pCtx->setState(tf::context::Done);
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
                    close();
                }
            });
        }
        // else ignore
    }

    void TransferSender::close()
    {
        socket.close();
        globalLog()->info("sender close");
    }

    void TransferSender::setContext(shared_ptr<TransferContext> pCtx)
    {
        this->pCtx = pCtx;
    }

    void TransferSender::doConnect(const tcp::resolver::results_type & endpoints)
    {
        asio::async_connect(socket, endpoints,
            [this](std::error_code ec, tcp::endpoint)
        {
            if (!ec)
            {
                globalLog()->info("sender connected");
            }
        });
    }

}

