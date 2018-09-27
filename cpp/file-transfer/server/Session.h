#pragma once

using namespace std;
using asio::ip::tcp;

enum State
{
    ReceiveLength = 0, // ���ڻ�ȡ���ݳ���״̬
    ReceiveData        // ���ڻ�ȡ��������
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
    uint32_t dataLength;  // �ļ���С��λ Byte
    uint32_t dataRemain;  // ���ж�����Ҫ����

    size_t bufMaxSize;    // ���� buf ����ܶ�ȡ�Ĵ�С
    size_t bufReadSize;   // async_read ʱӦ�ö�ȡ�� Byte ����
    vector<Byte> buf;
    State state;

    string tmpFileName;
    shared_ptr<ofstream> pfile;
};

