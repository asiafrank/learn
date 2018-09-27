#pragma once

using namespace std;
using asio::ip::tcp;

class Bootstrap
{
public:
    Bootstrap(asio::io_context& io_context, short port);
    ~Bootstrap();
private:
    void doAccept();
private:
    tcp::acceptor acceptor;
    tcp::socket socket;
};

