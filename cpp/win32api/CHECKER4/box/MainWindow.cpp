#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define DIVISIONS 5

TCHAR szChildClass[] = TEXT("Checker_Child");
int idFocus = 0;

LRESULT CALLBACK ChildWndProc(HWND, UINT, WPARAM, LPARAM);

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static HWND hwndChild[DIVISIONS][DIVISIONS];
    int cxBlock, cyBlock, x, y;

    static WNDCLASS wc = { 0 };

    switch (uMsg)
    {
    case WM_CREATE:
        wc.lpfnWndProc = ChildWndProc;
        wc.hInstance = m_hInst;
        wc.lpszClassName = szChildClass;
        wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
        wc.hCursor = LoadCursor(NULL, IDC_ARROW);
        wc.cbWndExtra = sizeof(long);

        RegisterClass(&wc);
        for (x = 0; x < DIVISIONS; x++)
        {
            for (y = 0; y < DIVISIONS; y++)
            {
                hwndChild[x][y] = CreateWindow(szChildClass, NULL, WS_CHILDWINDOW | WS_VISIBLE,
                    0, 0, 0, 0, m_hwnd, (HMENU)(y << 8 | x), 
                    (HINSTANCE)GetWindowLong(m_hwnd, GWL_HINSTANCE), 
                    NULL);
            }
        }
        break;
    case WM_SIZE:
        cxBlock = LOWORD(lParam) / DIVISIONS;
        cyBlock = HIWORD(lParam) / DIVISIONS;
        for (x = 0; x < DIVISIONS; x++)
            for (y = 0; y < DIVISIONS; y++)
                MoveWindow(hwndChild[x][y], x * cxBlock, y * cyBlock, cxBlock, cyBlock, TRUE);
        break;
    case WM_LBUTTONDOWN:
        MessageBeep(0);
        break;
    // On set-focus message, set focus to child window
    case WM_SETFOCUS:
        SetFocus(GetDlgItem(m_hwnd, idFocus));
        break;
    // On key-down message, possibly change the focus window
    case WM_KEYDOWN:
        x = idFocus & 0xFF;
        y = idFocus >> 8;
        switch (wParam)
        {
        case VK_UP: y--; break;
        case VK_DOWN: y++; break;
        case VK_LEFT: x--; break;
        case VK_RIGHT: x++; break;
        case VK_HOME: x = y = 0; break;
        case VK_END: x = y = DIVISIONS - 1; break;
        default: return 0;
        }

        x = (x + DIVISIONS) % DIVISIONS;
        y = (y + DIVISIONS) % DIVISIONS;

        idFocus = y << 8 | x;
        SetFocus(GetDlgItem(m_hwnd, idFocus));
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}

LRESULT CALLBACK ChildWndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    HDC hdc;
    PAINTSTRUCT ps;
    RECT rect;

    switch (msg)
    {
    case WM_CREATE:
        SetWindowLong(hwnd, 0, 0); // on/off flag
        break;
    case WM_KEYDOWN:
        // Send most key presses to the parent window
        if (wParam != VK_RETURN && wParam != VK_SPACE)
        {
            SendMessage(GetParent(hwnd), msg, wParam, lParam);
            return 0;
        }
        // For Return and Space, fall through to toggle the spuare
    case WM_LBUTTONDOWN:
        SetWindowLong(hwnd, 0, 1 ^ GetWindowLong(hwnd, 0));
        SetFocus(hwnd);
        InvalidateRect(hwnd, NULL, FALSE);
        break;
        // For focus messages,invalidate the window for repaint
    case WM_SETFOCUS:
        idFocus = GetWindowLong(hwnd, GWL_ID);
        // Fall through
    case WM_KILLFOCUS:
        InvalidateRect(hwnd, NULL, TRUE);
        break;
    case WM_PAINT:
        hdc = BeginPaint(hwnd, &ps);
        GetClientRect(hwnd, &rect);
        Rectangle(hdc, 0, 0, rect.right, rect.bottom);

        // Draw the "x" mark
        if (GetWindowLong(hwnd, 0))
        {
            MoveToEx(hdc, 0, 0, NULL);
            LineTo(hdc, rect.right, rect.bottom);
            MoveToEx(hdc, 0, rect.bottom, NULL);
            LineTo(hdc, rect.right, 0);
        }

        // Draw the "focus" rectangle
        if (hwnd == GetFocus())
        {
            rect.left += rect.right / 10;
            rect.right -= rect.left;
            rect.top += rect.bottom / 10;
            rect.bottom -= rect.top;

            SelectObject(hdc, GetStockObject(NULL_BRUSH));
            SelectObject(hdc, CreatePen(PS_DASH, 0, 0));
            Rectangle(hdc, rect.left, rect.top, rect.right, rect.bottom);
            DeleteObject(SelectObject(hdc, GetStockObject(BLACK_PEN)));
        }
        EndPaint(hwnd, &ps);
        break;
    default:
        return DefWindowProc(hwnd, msg, wParam, lParam);
    }
    return 0;
}
