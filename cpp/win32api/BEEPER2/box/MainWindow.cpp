#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define ID_TIMER 1

VOID CALLBACK TimerProc(HWND, UINT, UINT_PTR, DWORD);

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static BOOL fFlipFlop = FALSE;
    HBRUSH hBrush;
    HDC hdc;
    PAINTSTRUCT ps;
    RECT rc;

    switch (uMsg)
    {
    case WM_CREATE:
        SetTimer(m_hwnd, ID_TIMER, 1000, TimerProc);
        break;
    case WM_DESTROY:
        KillTimer(m_hwnd, ID_TIMER);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}

VOID TimerProc(HWND hwnd, UINT msg, UINT_PTR iTimerID, DWORD)
{
    static BOOL fFlipFlop = FALSE;
    HBRUSH hBrush;
    HDC hdc;
    RECT rc;

    MessageBeep(-1);
    fFlipFlop = !fFlipFlop;

    GetClientRect(hwnd, &rc);
    hdc = GetDC(hwnd);
    hBrush = CreateSolidBrush(fFlipFlop ? RGB(255, 0, 0) : RGB(0, 0, 255));

    FillRect(hdc, &rc, hBrush);
    ReleaseDC(hwnd, hdc);
    DeleteObject(hBrush);
    return VOID();
}
