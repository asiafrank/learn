#ifndef UNICODE
#define UNICODE
#endif 

#include <string>

#include "MainWindow.h"

int WINAPI wWinMain(HINSTANCE hInstance, HINSTANCE, PWSTR pCmdLine, int nCmdShow)
{
    MainWindow win;
    int nWidth = 500;
    int nHeight = 250;
    int x = (GetSystemMetrics(SM_CXSCREEN) - nWidth) / 2;
    int y = (GetSystemMetrics(SM_CYSCREEN) - nHeight) / 2;

    BOOL isCreated = win.Create(
        L"Learn to Program Windows", 
        WS_OVERLAPPEDWINDOW,
        0,
        x, y, nWidth, nHeight);

    if (!isCreated)
    {
        return 0;
    }

    ShowWindow(win.Window(), nCmdShow);

    // Run the message loop.
    MSG msg = {};
    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    return 0;
}

LRESULT MainWindow::HandleMessage(UINT uMsg,
    WPARAM wParam, 
    LPARAM lParam) 
{
    switch (uMsg)
    {
    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(m_hwnd, &ps);
            FillRect(hdc, &ps.rcPaint, (HBRUSH)(COLOR_WINDOW + 1));
            EndPaint(m_hwnd, &ps);
        }
        return 0;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return TRUE;
}