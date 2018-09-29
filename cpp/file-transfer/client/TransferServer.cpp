#include "stdafx.h"
#include "TransferServer.h"

using namespace std;
using asio::ip::tcp;

namespace tf
{
    TransferServer::TransferServer(asio::io_context& io_context, short port) 
        : acceptor(io_context, tcp::endpoint(tcp::v4(), port)),
        socket(io_context)
    {
        doAccept();
    }

    TransferServer::~TransferServer()
    {
        globalLog()->info("server end");
    }

    void TransferServer::doAccept()
    {
        acceptor.async_accept(socket,
            [this](asio::error_code ec)
        {
            if (!ec)
            {
                make_shared<TransferReceiver>(std::move(socket))->start();
            }
            doAccept();
        }
        );
    }
}
