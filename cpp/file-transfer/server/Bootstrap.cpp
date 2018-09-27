#include "pch.h"
#include "Bootstrap.h"
#include "Session.h"

Bootstrap::Bootstrap(asio::io_context& io_context, short port)
    : acceptor(io_context, tcp::endpoint(tcp::v4(), port)),
    socket(io_context)
{
    doAccept();
}

Bootstrap::~Bootstrap()
{
    cout << "------- server end --------" << endl;
}

void Bootstrap::doAccept()
{
    acceptor.async_accept(socket,
        [this](asio::error_code ec)
        {
            if (!ec)
            {
                make_shared<Session>(std::move(socket))->start();
            }
            doAccept();
        }
    );
}
