#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

int idFocus;
WNDPROC OldScroll[3];

LRESULT CALLBACK ScrollProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    int id = GetWindowLong(hwnd, GWL_ID);
    switch (msg)
    {
    case WM_KEYDOWN:
        if (wParam == VK_TAB)
            SetFocus(GetDlgItem(GetParent(hwnd),
                               (id + (GetKeyState(VK_SHIFT) < 0 ? 2 : 1)) % 3));
        break;
    case WM_SETFOCUS:
        idFocus = id;
        break;
    }
    return CallWindowProc(OldScroll[id], hwnd, msg, wParam, lParam);
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam) 
{
    
    return DefWindowProc(hwnd, msg, wParam, lParam);
}

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static COLORREF crPrim[3] = { RGB(255, 0, 0), RGB(0, 255, 0), RGB(0, 0, 255) };
    static HBRUSH hBrush[3], hBrushStatic;
    static HWND hwndScroll[3], hwndLabel[3], hwndValue[3], hwndRect;
    static int color[3], cyChar;
    static RECT rcColor;
    static TCHAR *szColorLabel[] = { TEXT("Red"), TEXT("Green"), TEXT("Blue") };
    HINSTANCE hInstance;
    int i, cxClient, cyClient;
    TCHAR szBuffer[10];

    switch (uMsg)
    {
    case WM_CREATE:
        hInstance = (HINSTANCE)GetWindowLong(m_hwnd, GWL_HINSTANCE);
        // Create the white-rectangle window against which the
        // scroll bars will be positioned. The child window ID is 9;
        hwndRect = CreateWindow(TEXT("static"), NULL,
                                WS_CHILD | WS_VISIBLE | SS_WHITERECT,
                                0, 0, 0, 0,
                                m_hwnd, (HMENU)9, hInstance, NULL);

        for (i = 0; i < 3; i++)
        {
            // The three scroll bars have IDs 0, 1, and 2, with
            // scroll bar ranges from 0 through 255.
            hwndScroll[i] = CreateWindow(TEXT("scrollbar"), NULL,
                                         WS_CHILD | WS_VISIBLE |
                                         WS_TABSTOP | SBS_VERT,
                                         0, 0, 0, 0,
                                         m_hwnd, (HMENU)i, hInstance, NULL);
            SetScrollRange(hwndScroll[i], SB_CTL, 0, 255, FALSE);
            SetScrollPos(hwndScroll[i], SB_CTL, 0, FALSE);

            // The three color-name labels have IDs 3, 4, and 5,
            // and text strings "Red", "Green", "Blue".
            hwndLabel[i] = CreateWindow(TEXT("static"), szColorLabel[i],
                                        WS_CHILD | WS_VISIBLE | SS_CENTER,
                                        0, 0, 0, 0,
                                        m_hwnd, (HMENU)(i + 3), hInstance, NULL);

            // The three color-value text fields have IDs 6, 7,
            // and 8, and initial text strings of "0".
            hwndValue[i] = CreateWindow(TEXT("static"), TEXT("0"),
                                        WS_CHILD | WS_VISIBLE | SS_CENTER,
                                        0, 0, 0, 0,
                                        m_hwnd, (HMENU)(i + 6), hInstance, NULL);
            OldScroll[i] = (WNDPROC)SetWindowLong(hwndScroll[i], GWL_WNDPROC, (LONG)ScrollProc);
            hBrush[i] = CreateSolidBrush(crPrim[i]);
        }

        hBrushStatic = CreateSolidBrush(GetSysColor(COLOR_BTNHIGHLIGHT));
        cyChar = HIWORD(GetDialogBaseUnits());
        break;
    case WM_SIZE:
        cxClient = LOWORD(lParam);
        cyClient = HIWORD(lParam);
        SetRect(&rcColor, cxClient / 2, 0, cxClient, cyClient);
        MoveWindow(hwndRect, 0, 0, cxClient / 2, cyChar, TRUE);

        for (i = 0; i < 3; i++)
        {
            MoveWindow(hwndScroll[i], (2 * i + 1) * cxClient / 14, 2 * cyChar,
                                      cxClient / 14, cyClient - 4 * cyChar, TRUE);

            MoveWindow(hwndLabel[i], (4 * i + 1) * cxClient / 28, cyChar / 2,
                                     cxClient / 7, cyChar, TRUE);

            MoveWindow(hwndValue[i], (4 * i + 1) * cxClient / 28, cyClient - 3 * cyChar / 2,
                                     cxClient / 7, cyChar, TRUE);
        }
        SetFocus(m_hwnd);
        break;
    case WM_SETFOCUS:
        SetFocus(hwndScroll[idFocus]);
        break;
    case WM_VSCROLL:
        i = GetWindowLong((HWND)lParam, GWL_ID);
        switch (LOWORD(wParam))
        {
        case SB_PAGEDOWN:
            color[i] += 15;
            // fall through
        case SB_LINEDOWN:
            color[i] = min(255, color[i] + 1);
            break;
        case SB_PAGEUP:
            color[i] -= 15;
            // fall throught
        case SB_LINEUP:
            color[i] = max(0, color[i] - 1);
            break;
        case SB_TOP:
            color[i] = 0;
            break;
        case SB_BOTTOM:
            color[i] = 255;
            break;
        case SB_THUMBPOSITION:
        case SB_THUMBTRACK:
            color[i] = HIWORD(wParam);
            break;
        }

        SetScrollPos(hwndScroll[i], SB_CTL, color[i], TRUE);
        wsprintf(szBuffer, TEXT("%i"), color[i]);
        SetWindowText(hwndValue[i], szBuffer);

        DeleteObject((HBRUSH)SetClassLong(m_hwnd, GCL_HBRBACKGROUND,
            (LONG)CreateSolidBrush(RGB(color[0], color[1], color[2]))));
        InvalidateRect(m_hwnd, &rcColor, TRUE);
        break;
    case WM_CTLCOLORSCROLLBAR:
        i = GetWindowLong((HWND)lParam, GWL_ID);
        return (LRESULT)hBrush[i];
    case WM_CTLCOLORSTATIC:
        i = GetWindowLong((HWND)lParam, GWL_ID);

        if (i >= 3 && i <= 8) // static text controls
        {
            SetTextColor((HDC)wParam, crPrim[i % 3]);
            SetBkColor((HDC)wParam, GetSysColor(COLOR_BTNHIGHLIGHT));
            return (LRESULT)hBrushStatic;
        }
        break;
    case WM_SYSCOLORCHANGE:
        DeleteObject(hBrushStatic);
        hBrushStatic = CreateSolidBrush(GetSysColor(COLOR_BTNHIGHLIGHT));
        break;
    case WM_DESTROY:
        DeleteObject((HBRUSH)SetClassLong(m_hwnd, GCL_HBRBACKGROUND,
                     (LONG)GetStockObject(WHITE_BRUSH)));
        for (i = 0; i < 3; i++)
        {
            DeleteObject(hBrush[i]);
        }

        DeleteObject(hBrushStatic);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
