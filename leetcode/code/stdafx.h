// stdafx.h : include file for standard system include files,
// or project specific include files that are used frequently, but
// are changed infrequently
//

#pragma once

#include "targetver.h"

#define WIN32_LEAN_AND_MEAN             // Exclude rarely-used stuff from Windows headers
// Windows Header Files:
#include <windows.h>

#define LIBRARY_EXPORTS

#ifdef LIBRARY_EXPORTS
#define API __declspec(dllexport)   
#else
#define API __declspec(dllimport)   
#endif 

// TODO: reference additional headers your program requires here
