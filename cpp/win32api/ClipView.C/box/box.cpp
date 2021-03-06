// box.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "box.h"

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_     HINSTANCE hInstance,
                      _In_opt_ HINSTANCE hPrevInstance,
                      _In_     LPWSTR    lpCmdLine,
                      _In_     int       nCmdShow)
{
    static TCHAR szAppName[] = L"ClipView";
    HWND hwnd;
    MSG msg;
    WNDCLASS wndclass;

    wndclass.style         = CS_HREDRAW | CS_VREDRAW;
    wndclass.lpfnWndProc   = WndProc;
    wndclass.cbClsExtra    = 0;
    wndclass.cbWndExtra    = 0;
    wndclass.hInstance     = hInstance;
    wndclass.hIcon         = LoadIcon(NULL, IDI_APPLICATION);
    wndclass.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wndclass.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wndclass.lpszMenuName  = NULL;
    wndclass.lpszClassName = szAppName;

    if (!RegisterClass(&wndclass))
    {
        MessageBox(NULL, L"This program requires Windows NT!", 
                   szAppName, MB_ICONERROR);
        return 0;
    }

    hwnd = CreateWindow(szAppName, L"Simple Clipboard Viewer (Text Only)",
                        WS_OVERLAPPEDWINDOW,
                        CW_USEDEFAULT, CW_USEDEFAULT,
                        CW_USEDEFAULT, CW_USEDEFAULT,
                        NULL, NULL, hInstance, NULL);

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    return msg.wParam;
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    static HWND hwndNextViewer;
    HGLOBAL hGlobal;
    HDC hdc;
    PTSTR pGlobal;
    PAINTSTRUCT ps;
    RECT rect;

    switch (message)
    {
    case WM_CREATE:
        hwndNextViewer = SetClipboardViewer(hwnd);
        return 0;
    case WM_CHANGECBCHAIN:
        if ((HWND)wParam == hwndNextViewer)
            hwndNextViewer = (HWND)lParam;
        else if (hwndNextViewer)
            SendMessage(hwndNextViewer, message, wParam, lParam);
        return 0;
    case WM_DRAWCLIPBOARD:
        if (hwndNextViewer)
            SendMessage(hwndNextViewer, message, wParam, lParam);
        InvalidateRect(hwnd, NULL, TRUE);
        return 0;
    case WM_PAINT:
        hdc = BeginPaint(hwnd, &ps);
        GetClientRect(hwnd, &rect);
        OpenClipboard(hwnd);

#ifdef UNICODE
        hGlobal = GetClipboardData(CF_UNICODETEXT);
#else
        hGlobal = GetClipboardData(CF_TEXT);
#endif // UNICODE
        if (hGlobal != NULL)
        {
            pGlobal = (PTSTR)GlobalLock(hGlobal);
            DrawText(hdc, pGlobal, -1, &rect, DT_EXPANDTABS);
            GlobalUnlock(hGlobal);
        }
        
        CloseClipboard();
        EndPaint(hwnd, &ps);
        return 0;
    case WM_DESTROY:
        ChangeClipboardChain(hwnd, hwndNextViewer);
        PostQuitMessage(0);
        return 0;
    }
    return DefWindowProc(hwnd, message, wParam, lParam);
}
