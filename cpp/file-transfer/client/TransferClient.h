#pragma once
#include "TransferContext.h"

using namespace std;
using asio::ip::tcp;

namespace tf
{
    namespace client
    {
        enum ClientState
        {
            Waiting = 10,  // 等待传输
            SendingLength,      // 正在传输文件大小
            SendingData,      // 正在传输文件内容
            Done          // 传输结束
        };
    }

    class TransferClient
    {
    public:
        TransferClient(asio::io_context& io_context,
            const tcp::resolver::results_type& endpoints);
        ~TransferClient();

        /* return 0 success, -1 fail */
        int sendFile(string filePath);
        void close();

        void setContext(shared_ptr<TransferContext> pCtx);
    private:
        void doConnect(const tcp::resolver::results_type& endpoints);
        void doSend();
    private:
        asio::io_context& io_context;
        tcp::socket socket;

        size_t remain;         // 还剩多少需要传输
        size_t bufMaxSize;     // 限制 buf 最多能读取的大小
        size_t bufWriteSize;   // async_write 时应该写入的 Byte 个数
        vector<Byte> buf;

        string currentFilePath; // 当前要传输的文件
        client::ClientState state;      // 当前文件的传输状态
        shared_ptr<ifstream> pfile;

        size_t fileLength;
        bool fileLengthSended;

        shared_ptr<TransferContext> pCtx;
    };
}


