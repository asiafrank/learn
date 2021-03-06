// box.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "box.h"
#include <winspool.h>

HDC GetPrinterDC(void)
{
    DWORD dwNeeded, dwReturned;
    HDC hdc;
    PRINTER_INFO_4 * pinfo4;

    // GetVersion is deprecated, so remove the if else statement
    EnumPrinters(PRINTER_ENUM_LOCAL, NULL, 4, NULL,
        0, &dwNeeded, &dwReturned);
    pinfo4 = (PRINTER_INFO_4*)malloc(dwNeeded);
    EnumPrinters(PRINTER_ENUM_LOCAL, NULL, 4, (PBYTE)pinfo4,
        dwNeeded, &dwNeeded, &dwReturned);
    hdc = CreateDC(NULL, pinfo4->pPrinterName, NULL, NULL);
    free(pinfo4);
    return hdc;
}

int APIENTRY wWinMain(_In_     HINSTANCE hInstance,
                      _In_opt_ HINSTANCE hPrevInstance,
                      _In_     LPWSTR    lpCmdLine,
                      _In_     int       nCmdShow)
{
    static DOCINFO di = {sizeof(DOCINFO), L"FormFeed"};
    HDC hdcPrint = GetPrinterDC();
    
    if (hdcPrint != NULL)
    {
        if (StartDoc(hdcPrint, &di) > 0)
        {
            if (StartPage(hdcPrint) > 0 && EndPage(hdcPrint) > 0)
                EndDoc(hdcPrint);
        }
        DeleteDC(hdcPrint);
    }
    return 0;
}