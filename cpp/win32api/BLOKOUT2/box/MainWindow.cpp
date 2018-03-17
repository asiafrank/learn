#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

void DrawBoxOutline(HWND hwnd, POINT ptBeg, POINT ptEnd)
{
    HDC hdc;
    hdc = GetDC(hwnd);
    SetROP2(hdc, R2_NOT);
    SelectObject(hdc, GetStockObject(NULL_BRUSH));
    Rectangle(hdc, ptBeg.x, ptBeg.y, ptEnd.x, ptEnd.y);
    ReleaseDC(hwnd, hdc);
}

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static BOOL fBlocking, fValidBox;
    static POINT ptBeg, ptEnd, ptBoxBeg, ptBoxEnd;
    HDC hdc;
    PAINTSTRUCT ps;

    switch (uMsg)
    {
    case WM_LBUTTONDOWN:
        ptBeg.x = ptEnd.x = LOWORD(lParam);
        ptBeg.y = ptEnd.y = HIWORD(lParam);

        DrawBoxOutline(m_hwnd, ptBeg, ptEnd);
        SetCapture(m_hwnd);
        SetCursor(LoadCursor(NULL, IDC_CROSS));
        fBlocking = TRUE;
        break;
    case WM_MOUSEMOVE:
        if (fBlocking)
        {
            SetCursor(LoadCursor(NULL, IDC_CROSS));

            DrawBoxOutline(m_hwnd, ptBeg, ptEnd);
            ptEnd.x = LOWORD(lParam);
            ptEnd.y = HIWORD(lParam);
            DrawBoxOutline(m_hwnd, ptBeg, ptEnd);
        }
        break;
    case WM_LBUTTONUP:
        if (fBlocking)
        {
            DrawBoxOutline(m_hwnd, ptBeg, ptEnd);
            ptBoxBeg = ptBeg;
            ptBoxEnd.x = LOWORD(lParam);
            ptBoxEnd.y = HIWORD(lParam);
            ReleaseCapture();
            SetCursor(LoadCursor(NULL, IDC_ARROW));
            fBlocking = FALSE;
            fValidBox = TRUE;
            InvalidateRect(m_hwnd, NULL, TRUE);
        }
        break;
    case WM_CHAR:
        if (fBlocking & wParam == '\x1B') // i.e., Escape
        {
            DrawBoxOutline(m_hwnd, ptBeg, ptEnd);
            ReleaseCapture();
            SetCursor(LoadCursor(NULL, IDC_ARROW));
            fBlocking = FALSE;
        }
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        if (fValidBox)
        {
            SelectObject(hdc, GetStockObject(BLACK_BRUSH));
            Rectangle(hdc, ptBoxBeg.x, ptBoxBeg.y, ptBoxEnd.x, ptBoxEnd.y);
        }
        if (fBlocking)
        {
            SetROP2(hdc, R2_NOT);
            SelectObject(hdc, GetStockObject(NULL_BRUSH));
            Rectangle(hdc, ptBeg.x, ptBeg.y, ptEnd.x, ptEnd.y);
        }
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