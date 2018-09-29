#pragma once

using namespace std;
using asio::ip::tcp;

namespace tf
{
    /*
    文件接收方
    所有的 Receiver 都被 TransferServer 管理。
    Receiver 和远端的 Sender 一一对应。

    @see TransferSender
    @see TransferServer
    */
    class TransferReceiver : public enable_shared_from_this<TransferReceiver>
    {
    public:
        TransferReceiver(tcp::socket socket);
        ~TransferReceiver();

        void start();
        const receiver::State& getState() const;
    private:
        void doRead();
    private:
        tcp::socket socket;

        uint32_t dataLength;  // 文件大小单位 Byte
        uint32_t dataRemain;  // 还有多少需要接收

        size_t bufMaxSize;    // 限制 buf 最多能读取的大小
        size_t bufReadSize;   // async_read 时应该读取的 Byte 个数
        vector<Byte> buf;
        receiver::State state;

        string tmpFileName;
        shared_ptr<ofstream> pfile;
    };
} // namespace tf end
