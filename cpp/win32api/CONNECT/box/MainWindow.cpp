#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define MAXPOINTS 1000

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static POINT pt[MAXPOINTS];
    static int iCount;
    HDC hdc;
    int i, j;
    PAINTSTRUCT ps;

    switch (uMsg)
    {
    case WM_LBUTTONDOWN:
        iCount = 0;
        InvalidateRect(m_hwnd, NULL, TRUE);
        break;
    case WM_MOUSEMOVE:
        if (wParam & MK_LBUTTON && iCount < 1000)
        {
            pt[iCount].x = LOWORD(lParam);
            pt[iCount++].y = HIWORD(lParam);

            hdc = GetDC(m_hwnd);
            SetPixel(hdc, LOWORD(lParam), HIWORD(lParam), 0);
            ReleaseDC(m_hwnd, hdc);
        }
        break;
    case WM_LBUTTONUP:
        InvalidateRect(m_hwnd, NULL, FALSE);
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        SetCursor(LoadCursor(NULL, IDC_WAIT));
        ShowCursor(TRUE);
        for (i = 0; i < iCount - 1; i++)
        {
            for (j = 0; j < iCount; j++)
            {
                MoveToEx(hdc, pt[i].x, pt[i].y, NULL);
                LineTo(hdc, pt[j].x, pt[j].y);
            }
        }

        ShowCursor(FALSE);
        SetCursor(LoadCursor(NULL, IDC_ARROW));
        EndPaint(m_hwnd, &ps);
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
