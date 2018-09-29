#pragma once

/*
��������ͼ:

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
    ����ǰ�ͻ��˵���һ�������������ڽ��������ͻ��˵Ĵ�������

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

