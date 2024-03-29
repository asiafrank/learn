// server.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include "Bootstrap.h"

/*
asio example: 接收数据，直接将数据写入文件
消息说明：<数据大小><数据内容>
-  数据大小：占4个字节，也就是说最大支持 4G文件
-  数据内容：字节流

字节用 std::uint8_t 表示

由于该例子仅供测试，所以文件流都写到 test.data 中
*/
int main()
{
#if defined(DEBUG) | defined(_DEBUG)
    _CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
#endif

    //_crtBreakAlloc = 166;

    asio::io_context io_context;
    Bootstrap boot(io_context, 6882);
    try
    {
        io_context.run();
    }
    catch (std::exception& e)
    {
        cerr << "Exception: " << e.what() << endl;
    }
    cout << "main thread end" << endl;
}