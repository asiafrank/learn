#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define DIVISIONS 5

TCHAR szChildClass[] = TEXT("Checker_Child");

LRESULT CALLBACK ChildWndProc(HWND, UINT, WPARAM, LPARAM);

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static HWND hwndChild[DIVISIONS][DIVISIONS];
    int cxBlock, cyBlock, x, y;

    WNDCLASS wc = { 0 };
    wc.lpfnWndProc = ChildWndProc;
    wc.hInstance = m_hInst;
    wc.lpszClassName = szChildClass;
    wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.cbWndExtra = sizeof(long);

    RegisterClass(&wc);

    switch (uMsg)
    {
    case WM_CREATE:
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
    case WM_LBUTTONDOWN:
        SetWindowLong(hwnd, 0, 1 ^ GetWindowLong(hwnd, 0));
        InvalidateRect(hwnd, NULL, FALSE);
        break;
    case WM_PAINT:
        hdc = BeginPaint(hwnd, &ps);
        GetClientRect(hwnd, &rect);
        Rectangle(hdc, 0, 0, rect.right, rect.bottom);

        if (GetWindowLong(hwnd, 0))
        {
            MoveToEx(hdc, 0, 0, NULL);
            LineTo(hdc, rect.right, rect.bottom);
            MoveToEx(hdc, 0, rect.bottom, NULL);
            LineTo(hdc, rect.right, 0);
        }
        EndPaint(hwnd, &ps);
        break;
    default:
        return DefWindowProc(hwnd, msg, wParam, lParam);
    }
    return 0;
}
