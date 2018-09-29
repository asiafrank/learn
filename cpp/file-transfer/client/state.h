#pragma once

using namespace std;

namespace tf
{
    namespace context
    {
        enum State
        {
            Waiting,    // ���ڵȴ�
            Running,    // ������
            Done,       // ����
            Error       // ������ִ���
        };
    }

    namespace receiver
    {
        enum State
        {
            GettingLength, // ���ڻ�ȡ�ļ�����
            GettingData    // ���ڻ�ȡ�ļ�����
        };
    }

    namespace sender
    {
        enum State
        {
            Waiting,        // �ȴ�����
            SendingLength,  // ���ڴ����ļ���С
            SendingData,    // ���ڴ����ļ�����
            Done            // �������
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