#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define ID_TIMER 1

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
        SetTimer(m_hwnd, ID_TIMER, 1000, NULL);
        break;
    case WM_TIMER:
        MessageBeep(-1);
        fFlipFlop = !fFlipFlop;
        InvalidateRect(m_hwnd, NULL, FALSE);
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        GetClientRect(m_hwnd, &rc);
        hBrush = CreateSolidBrush(fFlipFlop ? RGB(255, 0, 0) : RGB(0, 0, 255));
        FillRect(hdc, &rc, hBrush);

        EndPaint(m_hwnd, &ps);
        DeleteObject(hBrush);
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