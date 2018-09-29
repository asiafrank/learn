#include "stdafx.h"
#include "TransferContext.h"

namespace tf
{
    TransferContext::TransferContext(const wstring& filePath)
        : filePath(filePath),
        state(context::Waiting),
        percent(0.0)
    {
        if (filePath.empty())
            throw std::exception("filePath not valid");

        size_t pos = filePath.find_last_of(separator());
        if (pos == wstring::npos)
            fileName = filePath;
        else
            fileName = filePath.substr(pos + 1);
    }

    TransferContext::~TransferContext()
    {
    }

    bool TransferContext::isCompleted()
    {
        return state == context::Done || state == context::Error;
    }

    void TransferContext::setState(const context::State& state)
    {
        this->state = state;
    }

    const context::State & TransferContext::getState() const
    {
        return state;
    }

    void TransferContext::setPercent(const float & percent)
    {
        if (percent >= 100.0)
            this->percent = 100.0;
        else
            this->percent = percent;
    }

    const float TransferContext::getPercent() const
    {
        return percent;
    }
}
