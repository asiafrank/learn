#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

struct BTN
{
    int iStyle;
    TCHAR * szText;
};

BTN button[] = {
    BS_PUSHBUTTON, TEXT("PUSHBUTTON"),
    BS_DEFPUSHBUTTON, TEXT("DEFPUSHBUTTON"),
    BS_CHECKBOX, TEXT("CHECKBOX"),
    BS_AUTOCHECKBOX, TEXT("AUTOCHECKBOX"),
    BS_RADIOBUTTON, TEXT("RADIOBUTTON"),
    BS_3STATE, TEXT("3STATE"),
    BS_AUTO3STATE, TEXT("AUTO3STATE"),
    BS_GROUPBOX, TEXT("GROUPBOX"),
    BS_AUTORADIOBUTTON, TEXT("AUTORADIO"),
    BS_OWNERDRAW, TEXT("OWNERDRAW")
};

#define NUM (sizeof button / sizeof button[0])

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static HWND hwndButton[NUM];
    static RECT rect;
    static TCHAR szTop[] = TEXT("message  wParam lParam");
    static TCHAR szUnd[] = TEXT("_______  ______ ______");
    static TCHAR szFormat[] = TEXT("%-16s%04X-%04X %04X-%04X");
    static TCHAR szBuffer[50];
    static int cxChar, cyChar;
    HDC hdc;
    PAINTSTRUCT ps;
    int i;

    switch (uMsg)
    {
    case WM_CREATE:
        cxChar = LOWORD(GetDialogBaseUnits());
        cyChar = HIWORD(GetDialogBaseUnits());
        for (i = 0; i < NUM; i++)
        {
            hwndButton[i] = CreateWindow(TEXT("button"), button[i].szText,
                WS_CHILD | WS_VISIBLE | button[i].iStyle,
                cxChar, cyChar * (1 + 2 * i),
                20 * cxChar, 7 * cyChar / 4,
                m_hwnd, (HMENU)i,
                ((LPCREATESTRUCT)lParam)->hInstance, NULL);
        }
        break;
    case WM_SIZE:
        rect.left = 24 * cxChar;
        rect.top = 2 * cyChar;
        rect.right = LOWORD(lParam);
        rect.bottom = HIWORD(lParam);
        break;
    case WM_PAINT:
        InvalidateRect(m_hwnd, &rect, TRUE);
        hdc = BeginPaint(m_hwnd, &ps);
        SelectObject(hdc, GetStockObject(SYSTEM_FIXED_FONT));
        SetBkMode(hdc, TRANSPARENT);

        TextOut(hdc, 24 * cxChar, cyChar, szTop, lstrlen(szTop));
        TextOut(hdc, 24 * cxChar, cyChar, szUnd, lstrlen(szUnd));

        EndPaint(m_hwnd, &ps);
        break;
    case WM_DRAWITEM:
    case WM_COMMAND:
        ScrollWindow(m_hwnd, 0, -cyChar, &rect, &rect);
        hdc = GetDC(m_hwnd);
        SelectObject(hdc, GetStockObject(SYSTEM_FIXED_FONT));

        TextOut(hdc, 24 * cxChar, cyChar * (rect.bottom / cyChar - 1),
            szBuffer,
            wsprintf(szBuffer, szFormat, 
                uMsg == WM_DRAWITEM ? TEXT("WM_DRAWITEM") : TEXT("WM_COMMAND"), 
                HIWORD(wParam), LOWORD(wParam),
                HIWORD(lParam), LOWORD(lParam)));

        ReleaseDC(m_hwnd, hdc);
        ValidateRect(m_hwnd, &rect);
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
