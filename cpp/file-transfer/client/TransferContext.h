#pragma once

#include <string>

using namespace std;

namespace tf
{
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
        void setState(const context::State& s);
        const context::State& getState() const;
        void setPercent(const float& percent);
        const float getPercent() const;
    private:
        /*
        ���� TransferContext ��Ҫ�� windows GUI ���ϣ������ַ����ÿ��ַ�
        */
        
        wstring fileName;      // ������ļ�����������ǰ׺
        wstring filePath;      // �ļ�·��
        context::State state;  // ״̬
        float percent;         // ����ٷֱ�
    };
} // namespace tf end


