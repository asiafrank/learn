#pragma once

#include <string>

using namespace std;

namespace tf
{
    /*
    传输任务的上下文。为了简单，同一时间只支持一个传输任务
    UN-THREAD-SAFE
    */
    class TransferContext
    {
    public:
        /*
        throw exception: 当 filePath 不合法时抛出异常
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
        由于 TransferContext 需要和 windows GUI 整合，这里字符串用宽字符
        */
        
        wstring fileName;      // 传输的文件名，不包含前缀
        wstring filePath;      // 文件路径
        context::State state;  // 状态
        float percent;         // 已传输的必上, 范围是 0-1, 如果要百分比形式，乘以 100 即可
    };
} // namespace tf end


