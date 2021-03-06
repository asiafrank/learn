// box.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "main.h"
#include <Commdlg.h>

PCWSTR szAppName = L"DibHeads";

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
int APIENTRY wWinMain(_In_     HINSTANCE hInstance,
                      _In_opt_ HINSTANCE hPrevInstance,
                      _In_     LPWSTR    lpCmdLine,
                      _In_     int       nCmdShow)
{
    HACCEL        hAccel;
    HWND          hwnd;
    MSG           msg;
    WNDCLASS      wndclass;

    wndclass.style         = CS_HREDRAW | CS_VREDRAW;
    wndclass.lpfnWndProc   = WndProc;
    wndclass.cbClsExtra    = 0;
    wndclass.cbWndExtra    = 0;
    wndclass.hInstance     = hInstance;
    wndclass.hIcon         = LoadIcon(NULL, IDI_APPLICATION);
    wndclass.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wndclass.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wndclass.lpszMenuName  = szAppName;
    wndclass.lpszClassName = szAppName;

    if (!RegisterClass(&wndclass))
    {
        MessageBox(NULL, L"This program requires Windows NT!",
                   szAppName, MB_ICONERROR);
        return 0;
    }

    hwnd = CreateWindow(szAppName, L"DIB Headers",
                        WS_OVERLAPPEDWINDOW,
                        CW_USEDEFAULT, CW_USEDEFAULT, 
                        CW_USEDEFAULT, CW_USEDEFAULT, 
                        NULL, NULL, hInstance, NULL);

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    hAccel = LoadAccelerators(hInstance, szAppName);
    while (GetMessage(&msg, NULL, 0, 0))
    {
        if (!TranslateAccelerator(hwnd, hAccel, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return msg.wParam;
}

void Printf(HWND hwnd, PCWSTR szFormat, ...)
{
    TCHAR szBuffer[1024];
    va_list pArgList;

    va_start(pArgList, szFormat);
    wvsprintf(szBuffer, szFormat, pArgList);
    va_end(pArgList);

    SendMessage(hwnd, EM_SETSEL, (WPARAM)-1, (LPARAM)-1);
    SendMessage(hwnd, EM_REPLACESEL, FALSE, (LPARAM)szBuffer);
    SendMessage(hwnd, EM_SCROLLCARET, 0, 0);
}

void DisplayDibHeaders(HWND hwnd, PCWSTR szFileName)
{
    static PCWSTR szInfoName[] =
    {
        L"BITMAPCOREHEADER",
        L"BITMAPV4HEADER",
        L"BITMAPV5HEADER"
    };

    static PCWSTR szCompression[] = 
    {
        L"BI_RLE8",
        L"BI_RLE4",
        L"BI_BITFIELDS",
        L"unknown"
    };

    BITMAPCOREHEADER *pbmch;
    BITMAPFILEHEADER *pbmfh;
    BITMAPV5HEADER   *pbmih;
    BOOL             bSuccess;
    DWORD            dwFileSize, dwHeightSize, dwBytesRead;
    HANDLE           hFile;
    int              i;
    PBYTE            pFile;
    PCWSTR           szV = L"";

    // Display the file name
    Printf(hwnd, L"File: %s\r\n\r\n", szFileName);

    // Open the file
    hFile = CreateFile(szFileName, GENERIC_READ, 
                       FILE_SHARE_READ, NULL, OPEN_EXISTING, 
                       FILE_FLAG_SEQUENTIAL_SCAN, NULL);
    if (hFile == INVALID_HANDLE_VALUE)
    {
        Printf(hwnd, L"Cannot open file.\r\n\r\n");
        return;
    }

    // Get the size of the file
    dwFileSize = GetFileSize(hFile, &dwHeightSize);
    if (dwHeightSize)
    {
        Printf(hwnd, L"Cannot deal with >4G files.\r\n\r\n");
        CloseHandle(hFile);
        return;
    }

    // Allocate memory for the file
    pFile = (PBYTE)malloc(dwFileSize);
    if (!pFile)
    {
        Printf(hwnd, L"Cannot allocate memory.\r\n\r\n");
        CloseHandle(hFile);
        return;
    }

    // Read the file
    SetCursor(LoadCursor(NULL, IDC_WAIT));
    ShowCursor(TRUE);
    
    bSuccess = ReadFile(hFile, pFile, dwFileSize, &dwBytesRead, NULL);
    ShowCursor(FALSE);
    SetCursor(LoadCursor(NULL, IDC_ARROW));

    if (!bSuccess || (dwBytesRead != dwFileSize))
    {
        Printf(hwnd, L"Could not read file.\r\n\r\n");
        CloseHandle(hFile);
        free(pFile);
        return;
    }

    // Close the file
    CloseHandle(hFile);
    // Display file size
    Printf(hwnd, L"File size = %u bytes\r\n\r\n", dwFileSize);
    // Display BITMAPFILEHEADER structure
    pbmfh = (BITMAPFILEHEADER*)pFile;
    Printf(hwnd, L"BITMAPFILEHEADER\r\n");
    Printf(hwnd, L"\t.bfType = 0x%X\r\n",      pbmfh->bfType);
    Printf(hwnd, L"\t.bfSize = %u\r\n",        pbmfh->bfSize);
    Printf(hwnd, L"\t.bfReserved1 = %u\r\n",   pbmfh->bfReserved1);
    Printf(hwnd, L"\t.bfReserved2 = %u\r\n",   pbmfh->bfReserved2);
    Printf(hwnd, L"\t.bfOffBits = %u\r\n\r\n", pbmfh->bfOffBits);

    // Determine which information structure we have
    pbmih = (BITMAPV5HEADER*)(pFile + sizeof(BITMAPFILEHEADER));
    switch (pbmih->bV5Size)
    {
    case sizeof(BITMAPCOREHEADER): i = 0; break;
    case sizeof(BITMAPINFOHEADER): i = 1; szV = L"i"; break;
    case sizeof(BITMAPV4HEADER):   i = 2; szV = L"V4"; break; 
    case sizeof(BITMAPV5HEADER):   i = 3; szV = L"V5"; break;
    default:
        Printf(hwnd, L"Unkown header size of %u.\r\n\r\n", pbmih->bV5Size);
        free(pFile);
        break;
    }

    Printf(hwnd, L"%s\r\n", szInfoName[i]);
    // Display the BITMAPCOREHEADER fields
    if (pbmih->bV5Size == sizeof(BITMAPCOREHEADER))
    {
        pbmch = (BITMAPCOREHEADER*)pbmih;
        Printf(hwnd, L"\t.bcSize = %u\r\n", pbmch->bcSize);
        Printf(hwnd, L"\t.bcWidth = %u\r\n", pbmch->bcWidth);
        Printf(hwnd, L"\t.bcHeight = %u\r\n", pbmch->bcHeight);
        Printf(hwnd, L"\t.bcPlanes = %u\r\n", pbmch->bcPlanes);
        Printf(hwnd, L"\t.bcBitCount = %u\r\n\r\n", pbmch->bcBitCount);
        free(pFile);
        return;
    }

    // Display the BITMAPINFOHEADER fields
    Printf(hwnd, L"\t.b%sSize = %u\r\n", szV, pbmih->bV5Size);
    Printf(hwnd, L"\t.b%sWidth = %i\r\n", szV, pbmih->bV5Width);
    Printf(hwnd, L"\t.b%sHeight = %i\r\n", szV, pbmih->bV5Height);
    Printf(hwnd, L"\t.b%sPlanes = %u\r\n", szV, pbmih->bV5Planes);
    Printf(hwnd, L"\t.b%sBitCount = %u\r\n", szV, pbmih->bV5BitCount);
    Printf(hwnd, L"\t.b%sCompression = %s\r\n", szV, szCompression[min(4, pbmih->bV5Compression)]);
    Printf(hwnd, L"\t.b%sSizeImage = %u\r\n", szV, pbmih->bV5SizeImage);
    Printf(hwnd, L"\t.b%sXPelsPerMeter = %i\r\n", szV, pbmih->bV5XPelsPerMeter);
    Printf(hwnd, L"\t.b%sYPersPerMeter = %i\r\n", szV, pbmih->bV5YPelsPerMeter);
    Printf(hwnd, L"\t.b%sClrUsed = %i\r\n", szV, pbmih->bV5ClrUsed);
    Printf(hwnd, L"\t.b%sClrImportant = %i\r\n\r\n", szV, pbmih->bV5ClrImportant);

    if (pbmih->bV5Size == sizeof(BITMAPINFOHEADER))
    {
        if (pbmih->bV5Compression == BI_BITFIELDS)
        {
            Printf(hwnd, L"Red Mask = %08X\r\n", pbmih->bV5RedMask);
            Printf(hwnd, L"Green Mask = %08X\r\n", pbmih->bV5GreenMask);
            Printf(hwnd, L"Blue Mask = %08X\r\n", pbmih->bV5BlueMask);
        }
        free(pFile);
        return;
    }

    // Display additional BITMAPV4HEADER fields
    Printf(hwnd, L"\t.b%sRedMask = %08X\r\n", szV, pbmih->bV5RedMask);
    Printf(hwnd, L"\t.b%sGreenMask = %08X\r\n", szV, pbmih->bV5GreenMask);
    Printf(hwnd, L"\t.b%sBlueMask = %08X\r\n", szV, pbmih->bV5BlueMask);
    Printf(hwnd, L"\t.b%sAlphaMask = %08X\r\n", szV, pbmih->bV5AlphaMask);
    Printf(hwnd, L"\t.b%sCSType = %u\r\n", szV, pbmih->bV5CSType);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzRed.ciexyzX = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzRed.ciexyzX);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzRed.ciexyzY = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzRed.ciexyzY);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzRed.ciexyzZ = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzRed.ciexyzZ);

    Printf(hwnd, L"\t.b%sEndPoints.ciexyzGreen.ciexyzX = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzGreen.ciexyzX);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzGreen.ciexyzY = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzGreen.ciexyzY);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzGreen.ciexyzZ = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzGreen.ciexyzZ);

    Printf(hwnd, L"\t.b%sEndPoints.ciexyzBlue.ciexyzX = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzBlue.ciexyzX);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzBlue.ciexyzY = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzBlue.ciexyzY);
    Printf(hwnd, L"\t.b%sEndPoints.ciexyzBlue.ciexyzZ = %08X\r\n", szV, pbmih->bV5Endpoints.ciexyzBlue.ciexyzZ);

    Printf(hwnd, L"\t.b%sGammaRed = %08X\r\n", szV, pbmih->bV5GammaRed);
    Printf(hwnd, L"\t.b%sGammaGreen = %08X\r\n", szV, pbmih->bV5GammaGreen);
    Printf(hwnd, L"\t.b%sGammaBlue = %08X\r\n\r\n", szV, pbmih->bV5GammaBlue);

    if (pbmih->bV5Size == sizeof(BITMAPV4HEADER))
    {
        free(pFile);
        return;
    }

    // Display additional BITMAPV5HEADER fields
    Printf(hwnd, L"\t.b%sIntent = %u\r\n", szV, pbmih->bV5Intent);
    Printf(hwnd, L"\t.b%sProfileData = %u\r\n", szV, pbmih->bV5ProfileData);
    Printf(hwnd, L"\t.b%sProfileSize = %u\r\n", szV, pbmih->bV5ProfileSize);
    Printf(hwnd, L"\t.b%sReserved = %u\r\n", szV, pbmih->bV5Reserved);
    
    free(pFile);
    return;
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    static HWND hwndEdit;
    static OPENFILENAME ofn;
    static TCHAR szFileName[MAX_PATH], szTitleName[MAX_PATH];
    static PCWSTR szFilter = L"Bitmap Files (*.BMP)0*.bmp\0All Files (*.*)\0*.*\0\0"; 

    switch (message)
    {
    case WM_CREATE:
        hwndEdit = CreateWindow(L"edit", NULL, 
                                WS_CHILD | WS_VISIBLE | WS_BORDER |
                                WS_VSCROLL | WS_HSCROLL | ES_MULTILINE | 
                                ES_AUTOVSCROLL | ES_READONLY, 
                                0, 0, 0, 0, 
                                hwnd, (HMENU)1, ((LPCREATESTRUCT)lParam)->hInstance, NULL);
        ofn.lStructSize       = sizeof(OPENFILENAME);
        ofn.hwndOwner         = hwnd;
        ofn.hInstance         = NULL;
        ofn.lpstrFilter       = szFilter;
        ofn.lpstrCustomFilter = NULL;
        ofn.nMaxCustFilter    = 0;
        ofn.nFilterIndex      = 0;
        ofn.lpstrFile         = szFileName;
        ofn.nMaxFile          = MAX_PATH;
        ofn.lpstrFileTitle    = szTitleName;
        ofn.nMaxFileTitle     = MAX_PATH;
        ofn.lpstrInitialDir   = NULL;
        ofn.lpstrTitle        = NULL;
        ofn.Flags             = 0;
        ofn.nFileOffset       = 0;
        ofn.nFileExtension    = 0;
        ofn.lpstrDefExt       = L"bmp";
        ofn.lCustData         = 0;
        ofn.lpfnHook          = NULL;
        ofn.lpTemplateName    = NULL;
        return 0;
    case WM_SIZE:
        MoveWindow(hwndEdit, 0, 0, LOWORD(lParam), HIWORD(lParam), TRUE);
        return 0;
    case WM_COMMAND:
        switch (LOWORD(wParam))
        {
        case IDM_FILE_OPEN:
            if (GetOpenFileName(&ofn))
                DisplayDibHeaders(hwndEdit, szFileName);
            return 0;
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;
    }
    return DefWindowProc(hwnd, message, wParam, lParam);
}