// box.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "box.h"
#include "MainWindow.h"

int APIENTRY wWinMain(
    _In_     HINSTANCE hInstance,
    _In_opt_ HINSTANCE hPrevInstance,
    _In_     LPWSTR    lpCmdLine,
    _In_     int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    MainWindow win;
    int nWidth = 600;
    int nHeight = 300;
    int x = (GetSystemMetrics(SM_CXSCREEN) - nWidth) / 2;
    int y = (GetSystemMetrics(SM_CYSCREEN) - nHeight) / 2;

    BOOL isCreated = win.Create(
        L"TOOL BOX",
        WS_OVERLAPPEDWINDOW | WS_VSCROLL | WS_HSCROLL,
        0,
        x, y, nWidth, nHeight);

    if (!isCreated)
    {
        return 0;
    }

    ShowWindow(win.Window(), nCmdShow);

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_BOX));

    MSG msg;

    // Main message loop:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int)msg.wParam;
}