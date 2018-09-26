#pragma once

#include <string>

using namespace std;

namespace tf
{
    enum State
    {
        Waiting = 0, // ���ڵȴ�
        Running,     // ������
        Done,        // ����
        Error        // ������ִ���
    };

    /*
    ��������������ġ�Ϊ�˼򵥣�ͬһʱ��ֻ֧��һ����������
    UN-THREAD-SAFE
    */
    class TransferContext
    {
    public:
        /*
        throw exception: �� filePath ���Ϸ�ʱ�׳��쳣
        */
        TransferContext(const wstring& filePath);
        TransferContext(const TransferContext&) = delete;
        TransferContext& operator=(const TransferContext&) = delete;
        ~TransferContext();
    public:
        bool isCompleted();
        void setState(const State& s);
        const State& getState() const;
        void setPercent(const float& percent);
        const float getPercent() const;
    private:
        wstring fileName;  // ������ļ�����������ǰ׺
        wstring filePath;  // �ļ�·��
        State state;      // ״̬
        float percent;    // ����ٷֱ�
    };
} // namespace tf end


