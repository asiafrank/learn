#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define ID_TIMER 1

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static COLORREF cr, crLast;
    static HDC hdcScreen;
    HDC hdc;
    PAINTSTRUCT ps;
    POINT pt;
    RECT rc;
    TCHAR szBuffer[16];

    switch (uMsg)
    {
    case WM_CREATE:
        hdcScreen = CreateDC(TEXT("DISPLAY"), NULL, NULL, NULL);
        SetTimer(m_hwnd, ID_TIMER, 100, NULL);
        break;
    case WM_TIMER:
        GetCursorPos(&pt);
        cr = GetPixel(hdcScreen, pt.x, pt.y);
        SetPixel(hdcScreen, pt.x, pt.y, 0);

        if (cr != crLast)
        {
            crLast = cr;
            InvalidateRect(m_hwnd, NULL, FALSE);
        }
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);

        GetClientRect(m_hwnd, &rc);
        wsprintf(szBuffer, TEXT(" %02X %02X %02X "), GetRValue(cr), GetGValue(cr), GetBValue(cr));

        DrawText(hdc, szBuffer, -1, &rc, DT_SINGLELINE | DT_CENTER | DT_VCENTER);

        EndPaint(m_hwnd, &ps);
        break;
    case WM_DESTROY:
        DeleteDC(hdcScreen);
        KillTimer(m_hwnd, ID_TIMER);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
