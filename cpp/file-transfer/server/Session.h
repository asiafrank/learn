#pragma once

using namespace std;
using asio::ip::tcp;

enum State
{
    ReceiveLength = 0, // 正在获取数据长度状态
    ReceiveData        // 正在获取数据内容
};

class Session : public enable_shared_from_this<Session>
{
public:
    Session(tcp::socket socket);
    ~Session();

    void start();
private:
    void doRead();
private:
    tcp::socket socket;
    uint32_t dataLength;  // 文件大小单位 Byte
    uint32_t dataRemain;  // 还有多少需要接收

    size_t bufMaxSize;    // 限制 buf 最多能读取的大小
    size_t bufReadSize;   // async_read 时应该读取的 Byte 个数
    vector<Byte> buf;
    State state;

    string tmpFileName;
    shared_ptr<ofstream> pfile;
};

