#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static struct
    {
        int idStockFont;
        TCHAR * szStockFont;
    } stockfont[] = {
        OEM_FIXED_FONT, L"OEM_FIXED_FONT",
        ANSI_FIXED_FONT, L"ANSI_FIXED_FONT",
        SYSTEM_FONT, L"SYSTEM_FONT",
        DEVICE_DEFAULT_FONT, L"DEVICE_DEFAULT_FONT",
        SYSTEM_FIXED_FONT, L"SYSTEM_FIXED_FONT",
        DEFAULT_GUI_FONT, L"DEFAULT_GUI_FONT"
    };

    static int iFont, cFonts = sizeof stockfont / sizeof stockfont[0];
    HDC hdc;
    int i, x, y, cxGrid, cyGrid;
    PAINTSTRUCT ps;
    TCHAR szFaceName[LF_FACESIZE], szBuffer[LF_FACESIZE + 64];
    TEXTMETRIC tm;

    switch (uMsg)
    {
    case WM_CREATE:
        SetScrollRange(m_hwnd, SB_VERT, 0, cFonts - 1, TRUE);
        break;
    case WM_DISPLAYCHANGE:
        InvalidateRect(m_hwnd, NULL, TRUE);
        break;
    case WM_VSCROLL:
        switch (LOWORD(wParam))
        {
        case SB_TOP: iFont = 0; break;
        case SB_BOTTOM: iFont = cFonts - 1; break;
        case SB_LINEUP: 
        case SB_PAGEUP: iFont -= 1; break;
        case SB_LINEDOWN:
        case SB_PAGEDOWN: iFont += 1; break;
        case SB_THUMBPOSITION: iFont = HIWORD(wParam); break;
        }

        iFont = max(0, min(cFonts - 1, iFont));
        SetScrollPos(m_hwnd, SB_VERT, iFont, TRUE);
        InvalidateRect(m_hwnd, NULL, TRUE);
        break;
    case WM_KEYDOWN:
        switch (wParam)
        {
        case VK_HOME: SendMessage(m_hwnd, WM_VSCROLL, SB_TOP, 0); break;
        case VK_END: SendMessage(m_hwnd, WM_VSCROLL, SB_BOTTOM, 0); break;
        case VK_PRIOR:
        case VK_LEFT:
        case VK_UP: SendMessage(m_hwnd, WM_VSCROLL, SB_LINEUP, 0); break;
        case VK_NEXT:
        case VK_RIGHT:
        case VK_DOWN: SendMessage(m_hwnd, WM_VSCROLL, SB_PAGEDOWN, 0); break;
        }
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        SelectObject(hdc, GetStockObject(stockfont[iFont].idStockFont));
        GetTextFace(hdc, LF_FACESIZE, szFaceName);
        GetTextMetrics(hdc, &tm);
        cxGrid = max(3 * tm.tmAveCharWidth, 2 * tm.tmMaxCharWidth);
        cyGrid = tm.tmHeight + 3;

        TextOut(hdc, 0, 0, szBuffer, 
            wsprintf(szBuffer, TEXT(" %s: Face Name = %s, CharSet = %i"), 
            stockfont[iFont].szStockFont, szFaceName, tm.tmCharSet));

        SetTextAlign(hdc, TA_TOP | TA_CENTER);
        // vertical and horizontal lines
        for (i = 0; i < 17; i++)
        {
            MoveToEx(hdc, (i + 2) * cxGrid, 2 * cyGrid, NULL);
            LineTo(hdc, (i + 2) * cxGrid, 19 * cyGrid);

            MoveToEx(hdc, cxGrid, (i + 3) * cyGrid, NULL);
            LineTo(hdc, 18 * cxGrid, (i + 3) * cyGrid);
        }

        // vertical and horizontal headings
        for (i = 0; i < 16; i++)
        {
            TextOut(hdc, (2 * i + 5) * cxGrid / 2, 2 * cyGrid + 2, szBuffer, 
                wsprintf(szBuffer, TEXT("%X-"), i));

            TextOut(hdc, 3 * cxGrid / 2, (i + 3) * cyGrid + 2, szBuffer, 
                wsprintf(szBuffer, TEXT("-%X"), i));
        }

        // characters
        for (y = 0; y < 16; y++)
        {
            for (x = 0; x < 16; x++)
            {
                TextOut(hdc, (2 * x + 5) * cxGrid / 2, (y + 3) * cyGrid + 2, szBuffer, 
                    wsprintf(szBuffer, TEXT("%c"), 16 * x + y));
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
