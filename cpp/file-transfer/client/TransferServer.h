#pragma once

/*
大致流程图:

              A                                B
       +---------------+  send file     +--------------+
       |    Sender     |--------------->|   Receiver   |-------
       +---------------+                +--------------+      | managed by TransferServer
       +---------------+  send file     +--------------+
   ----|    Receiver   |<---------------|    Sender    |
   |   +---------------+                +--------------+
   managed by TransferServer
*/

using namespace std;
using asio::ip::tcp;

namespace tf
{
    /*
    将当前客户端当作一个服务器，用于接收其他客户端的传输任务

    @see TransferReicever
    @see TransferSender
    */
    class TransferServer
    {
    public:
        TransferServer(asio::io_context& io_context, short port);
        ~TransferServer();
    private:
        void doAccept();
    private:
        tcp::acceptor acceptor;
        tcp::socket socket;
    };
}

