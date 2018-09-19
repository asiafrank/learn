// asio-learn.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <asio.hpp>
#include <iostream>
#include <vector>
#include <map>

using namespace std;
using asio::ip::tcp;

class Session : public enable_shared_from_this<Session>
{
public:
    Session(tcp::socket socket)
        : socket_(std::move(socket))
    {}

    ~Session()
    {
        cout << "session released" << endl;
    }

    void start()
    {
        do_read();
    }
private:
    void do_read()
    {
        auto self(shared_from_this());
        asio::async_read_until(socket_, data_, "\r\n",
            [this, self](asio::error_code ec, size_t length)
            {
                if (!ec)
                {
                    do_write(length);
                }
            });
    }

    void do_write(size_t length)
    {
        auto self(shared_from_this());
        asio::async_write(socket_, data_,
            [this, self](asio::error_code ec, size_t length)
            {
                if (!ec)
                {
                    do_read();
                }
            });
    }
private:
    tcp::socket socket_;
    asio::streambuf data_;
};

class Server
{
public:
    Server(asio::io_context& io_context, short port)
        : acceptor_(io_context, tcp::endpoint(tcp::v4(), port)),
        socket_(io_context)
    {
        do_accept();
    }

    ~Server()
    {
        std::cout << "--------- server end ---------" << std::endl;
    }
private:
    void do_accept()
    {
        acceptor_.async_accept(socket_,
            [this](asio::error_code ec)
            {
                if (!ec)
                {
                    make_shared<Session>(std::move(socket_))->start();
                }

                do_accept();
            });
    }
private:
    tcp::acceptor acceptor_;
    tcp::socket socket_;
};

int main()
{
    asio::io_context io;
    asio::steady_timer t(io, asio::chrono::seconds(5));
    t.wait();
    std::cout << "Hello, world!" << std::endl;

    asio::ip::tcp::resolver resolver(io);
    asio::ip::tcp::resolver::query query("www.boost.org", "http");
    asio::ip::tcp::resolver::iterator iter = resolver.resolve(query);
    asio::ip::tcp::resolver::iterator end; // End marker.
    while (iter != end)
    {
        asio::ip::tcp::endpoint endpoint = *iter++;
        std::cout << endpoint << std::endl;
    }

    asio::ip::tcp::iostream stream;
    std::chrono::seconds sec(60);
    stream.expires_from_now(sec);
    stream.connect("www.boost.org", "http");
    stream << "GET /LICENSE_1_0.txt HTTP/1.0\r\n";
    stream << "Host: www.boost.org\r\n";
    stream << "Accept: */*\r\n";
    stream << "Connection: close\r\n\r\n";
    stream.flush();
    std::cout << stream.rdbuf();

    std::cout << "--------- async tcp echo server --------" << std::endl;

    try
    {
        asio::io_context io_context;
        Server s(io_context, 6881);
        io_context.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "Exception: " << e.what() << std::endl;
    }
    return 0;
}

