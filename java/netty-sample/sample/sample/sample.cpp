// sample.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"

#ifdef __cplusplus
extern "C" {
#endif // __cplusplus
typedef struct _CResult {
    LPCWSTR name;
    LPCWSTR description;
} CResult;

__declspec(dllexport) CResult scriptMain() {
    CResult rs;
    rs.name = L"cpp result";
    rs.description = L"This is cpp result";
    return rs;
}

#ifdef __cplusplus
}
#endif // _cplusplus
