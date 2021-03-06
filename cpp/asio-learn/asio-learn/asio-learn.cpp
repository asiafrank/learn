// asio-learn.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <asio.hpp>
#include <asio/ssl.hpp>
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
                cout << "async read until handler: length " << length << endl;
                if (!ec)
                {
                    do_write(length);
                }
            });
    }

    void do_write(size_t length)
    {
        cout << "do write length: " << length << endl;
        auto self(shared_from_this());
        asio::async_write(socket_, data_,
            [this, self](asio::error_code ec, size_t length)
            {
                cout << "async write handler: length " << length << endl;
                if (!ec)
                {
                    do_read();
                }
            });
    }
private:
    tcp::socket socket_;
    asio::streambuf data_;
    size_t lengthSum = 0;
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

void testSSL();
void testSome();

int main()
{
    testSSL();
    // testSome();
    /*std::cout << "--------- async tcp echo server --------" << std::endl;
    try
    {
        asio::io_context io_context;
        Server s(io_context, 6881);
        io_context.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "Exception: " << e.what() << std::endl;
    }*/
    return 0;
}

// 1.下载 OpenSSL: https://mirror.firedaemon.com/OpenSSL/openssl-1.1.0j-dev.zip
// 2.设置 C/C++ -> General -> Additional Include Directory. (D:\workspace\openssl-1.1\include;)
// 3.设置 Linker -> General -> Additional Library Directory. (D:\workspace\openssl-1.1\x64\lib;)
// 4.设置 Linker -> Input -> Additional Dependencies (libssl.lib;libcrypto.lib;)
// 5.设置 Debugging -> Environment (PATH=D:\workspace\openssl-1.1\x64\bin;%PATH%)
void testSSL()
{
    {
        // what we need
        asio::error_code ec;
        
        asio::io_context io_context;
        asio::ssl::context ctx(asio::ssl::context::method::sslv23_client);
        asio::ssl::stream<asio::ip::tcp::socket> ssock(io_context, ctx);
        tcp::resolver resolver(io_context);
        auto it = resolver.resolve({ "app.anitama.net", "https" });
        asio::connect(ssock.lowest_layer(), it);
        ssock.handshake(asio::ssl::stream_base::handshake_type::client);

        // send request
        std::ostringstream oss;
        oss << "GET /guide/today/all HTTP/1.1\r\n";
        oss << "Host: app.anitama.net\r\n";
        oss << "Accept: */*\r\n";
        oss << "Connection: close\r\n\r\n";
        asio::write(ssock, asio::buffer(oss.str()));

        // read response
        std::string response;

        do {
            char buf[1024];
            size_t bytes_transferred = ssock.read_some(asio::buffer(buf), ec);
            if (!ec) response.append(buf, buf + bytes_transferred);
        } while (!ec);

        // print and exit
        std::cout << "Response received: '" << response << "'\n";
    }
}

void testSome()
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

    {
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
    }
}
