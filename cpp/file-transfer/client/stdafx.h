// stdafx.h : include file for standard system include files,
// or project specific include files that are used frequently, but
// are changed infrequently
//

#pragma once

#include "targetver.h"

#define WIN32_LEAN_AND_MEAN             // Exclude rarely-used stuff from Windows headers
// Windows Header Files
#include <windows.h>

// C RunTime Header Files
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>
#include <crtdbg.h>
#include <malloc.h>
#include <memory.h>
#include <tchar.h>

// https://docs.microsoft.com/en-us/visualstudio/debugger/finding-memory-leaks-using-the-crt-library?view=vs-2017
#ifdef _DEBUG
#define DBG_NEW new ( _NORMAL_BLOCK , __FILE__ , __LINE__ )
// Replace _NORMAL_BLOCK with _CLIENT_BLOCK if you want the
// allocations to be of _CLIENT_BLOCK type
#else
#define DBG_NEW new
#endif

#include <commdlg.h>
#include <math.h>
#include <d2d1_3.h>
#include <d2d1_3helper.h>
#include <dwrite_3.h>
#include <wincodec.h>

#pragma comment(lib, "d2d1")
#pragma comment(lib, "dwrite")

// c++ 17 ignore warning
#define _SILENCE_CXX17_ALLOCATOR_VOID_DEPRECATION_WARNING
//#define _SILENCE_ALL_CXX17_DEPRECATION_WARNINGS

#define ASIO_STANDALONE 
#define ASIO_HAS_STD_ADDRESSOF
#define ASIO_HAS_STD_ARRAY
#define ASIO_HAS_CSTDINT
#define ASIO_HAS_STD_SHARED_PTR
#define ASIO_HAS_STD_TYPE_TRAITS

/*
VS 检测到了内存泄漏，原因是开启了 handler tracking，关闭它，内存泄露就不出现了
至于为什么出现，有空再寻
*/
// #define ASIO_ENABLE_HANDLER_TRACKING

#include <asio.hpp>

#include <spdlog/spdlog.h>
#include <spdlog/sinks/basic_file_sink.h>
#include <spdlog/sinks/rotating_file_sink.h>

#include <iostream>
#include <fstream>

// Windows COM Release
template<class Interface>
inline void SafeRelease(
    Interface **ppInterfaceToRelease
)
{
    if (*ppInterfaceToRelease != NULL)
    {
        (*ppInterfaceToRelease)->Release();

        (*ppInterfaceToRelease) = NULL;
    }
}

#ifndef Assert
#if defined( DEBUG ) || defined( _DEBUG )
#define Assert(b) do {if (!(b)) {OutputDebugStringA("Assert: " #b "\n");}} while(0)
#else
#define Assert(b)
#endif //DEBUG || _DEBUG
#endif

#ifndef HINST_THISCOMPONENT
EXTERN_C IMAGE_DOS_HEADER __ImageBase;
#define HINST_THISCOMPONENT ((HINSTANCE)&__ImageBase)
#endif

using Byte = std::uint8_t;

// spdlog
inline std::shared_ptr<spdlog::logger> globalLog()
{
    static auto plog = spdlog::get("global_log");
    if (plog)
        return plog;

    plog = spdlog::rotating_logger_mt("global_log", "file-transfer.log", 1048576 * 5, 3);
    return plog;
}

// 将 wstring 转换为 string
inline std::string narrow(const std::wstring &s)
{
    static UINT cp = GetACP();
    std::string str;
    int len = WideCharToMultiByte(cp, 0,
        s.c_str(), (int)s.length(),
        NULL, 0, NULL, NULL);
    if (len > 0)
    {
        str.resize(len);
        WideCharToMultiByte(cp, 0,
            s.c_str(), (int)s.length(), &str[0],
            len, NULL, NULL);
    }
    return str;
}

inline char separator()
{
#ifdef _WIN32
    return '\\';
#else
    return '/';
#endif
}

#include "state.h"
#include "TransferContext.h"
#include "TransferSender.h"
#include "TransferReceiver.h"
#include "TransferServer.h"

