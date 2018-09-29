#pragma once

using namespace std;
using asio::ip::tcp;

namespace tf
{
    /*
    �ļ����ͷ�
    һ�� Sender ֻ��һ��Զ�����ӣ�һ��ֻ�ܴ�һ���ļ�

    @see TransferReceiver
    @see TransferServer
    */
    class TransferSender
    {
    public:
        TransferSender(asio::io_context& io_context,
            const tcp::resolver::results_type& endpoints);
        ~TransferSender();

        /* return 0 success, -1 fail */
        int sendFile(string filePath);
        void close();

        void setContext(shared_ptr<TransferContext> pCtx);
    private:
        void doConnect(const tcp::resolver::results_type& endpoints);
        void doSend();
    private:
        asio::io_context& io_context;
        tcp::socket socket;

        size_t remain;         // ��ʣ������Ҫ����
        size_t bufMaxSize;     // ���� buf ����ܶ�ȡ�Ĵ�С
        size_t bufWriteSize;   // async_write ʱӦ��д��� Byte ����
        vector<Byte> buf;

        string currentFilePath;   // ��ǰҪ������ļ�
        sender::State state;      // ��ǰ�ļ��Ĵ���״̬
        shared_ptr<ifstream> pfile;

        size_t fileLength;
        bool fileLengthSended;

        shared_ptr<TransferContext> pCtx;
    };
}


