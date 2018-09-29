#pragma once

using namespace std;
using asio::ip::tcp;

namespace tf
{
    /*
    文件发送方
    一个 Sender 只和一个远端连接，一次只能传一个文件

    @see TransferReceiver
    @see TransferServer
    */
    class TransferSender
    {
    public:
        TransferSender(asio::io_context& io_context,
            const tcp::resolver::results_type& endpoints);
        ~TransferSender();

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

        string currentFilePath;   // 当前要传输的文件
        sender::State state;      // 当前文件的传输状态
        shared_ptr<ifstream> pfile;

        size_t fileLength;
        bool fileLengthSended;

        shared_ptr<TransferContext> pCtx;
    };
}


