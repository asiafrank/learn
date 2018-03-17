#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static int cxClientMax, cyClientMax, cxClient, cyClient, cxChar, cyChar;
    static int cLinesMax, cLines;
    static PMSG pmsg;
    static RECT rectScroll;
    static TCHAR szTop[] = TEXT("Message         Key      Char    Repeat Scan Ext ALT Prev Tran");
    static TCHAR szUnd[] = TEXT("_______        _____  __________ ______ ____ ___ ___ ____ ____");

    static TCHAR * szFormat[2] = {
        TEXT("%-13s %3d %-15s%c%6u %4d %3s %3s %4s %4s"),
        TEXT("%-13s 0x%04X%1s%c %6u %4d %3s %3s %4s %5s")
    };

    static TCHAR * szYes = TEXT("Yes");
    static TCHAR * szNo  = TEXT("No");
    static TCHAR * szDown = TEXT("Down");
    static TCHAR * szUp = TEXT("Up");
    static TCHAR * szMessage[] = {
        TEXT("WM_KEYDOWN"), TEXT("WM_KEYUP"),
        TEXT("WM_SYSKEYDOWN"), TEXT("WM_SYSKEYUP"),
        TEXT("WM_SYSCHAR"), TEXT("WM_SYSDEADCHAR")
    };
    HDC hdc;
    int i, iType;
    PAINTSTRUCT ps;
    TCHAR szBuffer[128], szKeyName[32];
    TEXTMETRIC tm;

    switch (uMsg)
    {
    case WM_CREATE:
    case WM_DISPLAYCHANGE:
        // Get maximum size of client area
        cxClientMax = GetSystemMetrics(SM_CXMAXIMIZED);
        cyClientMax = GetSystemMetrics(SM_CYMAXIMIZED);

        // Get character size for fixed-pitch font
        hdc = GetDC(m_hwnd);
        SelectObject(hdc, GetStockObject(SYSTEM_FIXED_FONT));
        GetTextMetrics(hdc, &tm);
        cxChar = tm.tmAveCharWidth;
        cyChar = tm.tmHeight;

        ReleaseDC(m_hwnd, hdc);
        // Allocate memory for display lines
        if (pmsg)
            free(pmsg);
        cLinesMax = cyClientMax / cyChar;
        pmsg = (MSG*)malloc(cLinesMax * sizeof(MSG));
        cLines = 0;
        // fall through
    case WM_SIZE:
        if (uMsg == WM_SIZE)
        {
            cxClient = LOWORD(lParam);
            cyClient = HIWORD(lParam);
        }
        // Calculate scrolling rectangle
        rectScroll.left = 0;
        rectScroll.right = cyClient;
        rectScroll.top = cyChar;
        rectScroll.bottom = cyChar * (cyClient / cyChar);

        InvalidateRect(m_hwnd, NULL, TRUE);
        break;
    case WM_KEYDOWN:
    case WM_KEYUP:
    case WM_CHAR:
    case WM_DEADCHAR:
    case WM_SYSKEYDOWN:
    case WM_SYSKEYUP:
    case WM_SYSCHAR:
    case WM_SYSDEADCHAR:
        // Rearrange storage array
        for (i = cLinesMax - 1; i > 0; i--)
        {
            pmsg[i] = pmsg[i - 1];
        }
        // Store new message
        pmsg[0].hwnd = m_hwnd;
        pmsg[0].message = uMsg;
        pmsg[0].wParam = wParam;
        pmsg[0].lParam = lParam;
        cLines = min(cLines + 1, cLinesMax);
        // Scroll up the display
        ScrollWindow(m_hwnd, 0, -cyChar, &rectScroll, &rectScroll);
        break; // i.e., call DefWindowProc so Sys messages work
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        SelectObject(hdc, GetStockObject(SYSTEM_FIXED_FONT));
        SetBkMode(hdc, TRANSPARENT);
        TextOut(hdc, 0, 0, szTop, lstrlen(szTop));
        TextOut(hdc, 0, 0, szUnd, lstrlen(szUnd));

        for (i = 0; i < min(cLines, cyClient / cyChar - 1); i++)
        {
            iType = pmsg[i].message == WM_CHAR ||
                pmsg[i].message == WM_SYSCHAR ||
                pmsg[i].message == WM_DEADCHAR ||
                pmsg[i].message == WM_SYSDEADCHAR;

            GetKeyNameText(pmsg[i].lParam, szKeyName, sizeof(szKeyName) / sizeof(TCHAR));

            TextOut(hdc, 0, (cyClient / cyChar - 1 - i) * cyChar, szBuffer,
                wsprintf(szBuffer, szFormat[iType],
                    szMessage[pmsg[i].message - WM_KEYFIRST],
                    pmsg[i].wParam,
                    (PTSTR)(iType ? TEXT(" ") : szKeyName), 
                    (TCHAR)(iType ? pmsg[i].wParam : ' '), 
                    LOWORD (pmsg[i].lParam), HIWORD (pmsg[i].lParam) & 0xFF, 
                    0x01000000 & pmsg[i].lParam ? szYes : szNo, 
                    0x20000000 & pmsg[i].lParam ? szYes : szNo, 
                    0x40000000 & pmsg[i].lParam ? szDown : szUp, 
                    0x80000000 & pmsg[i].lParam ? szUp : szDown));
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
