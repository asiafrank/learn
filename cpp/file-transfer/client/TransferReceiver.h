#pragma once

using namespace std;
using asio::ip::tcp;

namespace tf
{
    /*
    �ļ����շ�
    ���е� Receiver ���� TransferServer ����
    Receiver ��Զ�˵� Sender һһ��Ӧ��

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

        uint32_t dataLength;  // �ļ���С��λ Byte
        uint32_t dataRemain;  // ���ж�����Ҫ����

        size_t bufMaxSize;    // ���� buf ����ܶ�ȡ�Ĵ�С
        size_t bufReadSize;   // async_read ʱӦ�ö�ȡ�� Byte ����
        vector<Byte> buf;
        receiver::State state;

        string tmpFileName;
        shared_ptr<ofstream> pfile;
    };
} // namespace tf end
