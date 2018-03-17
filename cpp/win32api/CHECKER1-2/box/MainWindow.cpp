#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define DIVISIONS 5

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static BOOL fState[DIVISIONS][DIVISIONS];
    static int cxBlock, cyBlock;
    HDC hdc;
    int x, y;
    PAINTSTRUCT ps;
    POINT point;
    RECT rect;

    switch (uMsg)
    {
    case WM_SIZE:
        cxBlock = LOWORD(lParam) / DIVISIONS;
        cyBlock = HIWORD(lParam) / DIVISIONS;
        break;
    case WM_SETFOCUS:
        ShowCursor(TRUE);
        break;
    case WM_KILLFOCUS:
        ShowCursor(FALSE);
        break;
    case WM_KEYDOWN:
        GetCursorPos(&point);
        ScreenToClient(m_hwnd, &point);
        x = max(0, min(DIVISIONS - 1, point.x / cxBlock));
        y = max(0, min(DIVISIONS - 1, point.y / cyBlock));

        switch (wParam)
        {
        case VK_UP:
            y--;
            break;
        case VK_DOWN:
            y++;
            break;
        case VK_LEFT:
            x--;
            break;
        case VK_RIGHT:
            x++;
            break;
        case VK_HOME:
            x = y = 0;
            break;
        case VK_END:
            x = y = DIVISIONS - 1;
            break;
        case VK_RETURN:
        case VK_SPACE:
            SendMessage(m_hwnd, WM_LBUTTONDOWN, MK_LBUTTON, MAKELONG(x * cxBlock, y * cyBlock));
            break;
        }
        x = (x + DIVISIONS) % DIVISIONS;
        y = (y + DIVISIONS) % DIVISIONS;

        point.x = x * cxBlock + cxBlock / 2;
        point.y = y * cyBlock + cyBlock / 2;

        ClientToScreen(m_hwnd, &point);
        SetCursorPos(point.x, point.y);
        break;
    case WM_LBUTTONDOWN:
        x = LOWORD(lParam) / cxBlock;
        y = HIWORD(lParam) / cyBlock;

        if (x < DIVISIONS && y < DIVISIONS)
        {
            fState[x][y] ^= 1;
            rect.left = x * cxBlock;
            rect.top = y * cyBlock;
            rect.right = (x + 1) * cxBlock;
            rect.bottom = (y + 1) * cyBlock;

            InvalidateRect(m_hwnd, &rect, FALSE);
        }
        else
        {
            MessageBeep(0);
        }
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        for (x = 0; x < DIVISIONS; x++)
        {
            for (y = 0; y < DIVISIONS; y++)
            {
                Rectangle(hdc, x * cxBlock, y * cyBlock, (x + 1) * cxBlock, (y + 1) * cyBlock);
                if (fState[x][y])
                {
                    MoveToEx(hdc, x * cxBlock, y * cyBlock, NULL);
                    LineTo(hdc, (x + 1) * cxBlock, (y + 1) * cyBlock);
                    MoveToEx(hdc, x * cxBlock, (y + 1) * cyBlock, NULL);
                    LineTo(hdc, (x + 1) * cxBlock, y * cyBlock);
                }
            }
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
