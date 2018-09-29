#pragma once

using namespace std;

namespace tf
{
    namespace context
    {
        enum State
        {
            Waiting,    // 正在等待
            Running,    // 传输中
            Done,       // 结束
            Error       // 传输出现错误
        };
    }

    namespace receiver
    {
        enum State
        {
            GettingLength, // 正在获取文件长度
            GettingData    // 正在获取文件数据
        };
    }

    namespace sender
    {
        enum State
        {
            Waiting,        // 等待传输
            SendingLength,  // 正在传输文件大小
            SendingData,    // 正在传输文件内容
            Done            // 传输结束
        };
    }

    std::string enumToStr(context::State& s)
    {
        switch (s)
        {
        case context::Waiting:
            return "Waiting";
        case context::Running:
            return "Running";
        case context::Done:
            return "Done";
        case context::Error:
            return "Error";
        default:
            return "Unknown";
        }
    }

    std::string enumToStr(receiver::State& s)
    {
        switch (s)
        {
        case receiver::GettingLength:
            return "GettingLength";
        case receiver::GettingData:
            return "GettingData";
        default:
            return "Unknown";
        }
    }

    std::string enumToStr(sender::State& s)
    {
        switch (s)
        {
        case sender::Waiting:
            return "Waiting";
        case sender::SendingLength:
            return "SendingLength";
        case sender::SendingData:
            return "SendingData";
        case sender::Done:
            return "Done";
        default:
            return "Unknown";
        }
    }
} // namespace tf end