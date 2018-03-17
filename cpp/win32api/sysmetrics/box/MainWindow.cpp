#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "sysmets.h"

INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static int cxChar = 0;    // 平均字符宽度
    static int cyChar = 0;    // 总字符高度
    static int cxCaps = 0;    // 计算过的字体间距(包括字体本身的宽度)
    static int cxClient = 0;  // WM_SIZE 触发后获取的窗口宽度
    static int cyClient = 0;  // WM_SIZE 触发后获取的窗口高度
    static int iMaxWidth = 0; // 文本一行所占的宽度
   
    int iVertPos = 0;
    int iHorzPos = 0;
    int iPaintBeg = 0;
    int iPaintEnd = 0;
    int x, y;
    int lineNum;

    HINSTANCE hInst = GetModuleHandle(NULL);
    PAINTSTRUCT ps;
    SCROLLINFO si;
    HDC hdc;
    RECT rect;
    TEXTMETRIC tm;
    TCHAR szBuffer[10];
    switch (uMsg)
    {
    case WM_CREATE:
        hdc = GetDC(m_hwnd);
        GetTextMetrics(hdc, &tm);
        cxChar = tm.tmAveCharWidth;
        cxCaps = (tm.tmPitchAndFamily & TMPF_FIXED_PITCH ? 3 : 2) * cxChar / 2;
        cyChar = tm.tmHeight + tm.tmExternalLeading;
        ReleaseDC(m_hwnd, hdc);

        iMaxWidth = 40 * cxChar + 22 * cxCaps;
        break;
    case WM_SIZE:
        cxClient = LOWORD(lParam); // 低位是窗口宽度
        cyClient = HIWORD(lParam); // 高位是窗口高度

        // Set vertical scroll bar range and page size
        si.cbSize = sizeof(si);
        si.fMask = SIF_RANGE | SIF_PAGE;
        si.nMin = 0;
        si.nMax = NUMLINES - 1;
        si.nPage = cyClient / cyChar;
        SetScrollInfo(m_hwnd, SB_VERT, &si, TRUE);

        // Set horizontal scroll bar range and page size
        si.cbSize = sizeof(si);
        si.fMask = SIF_RANGE | SIF_PAGE;
        si.nMin = 0;
        si.nMax = 2 + iMaxWidth / cxChar;  // 一行最多显示字符数
        si.nPage = cxClient / cxChar;      // 设定页面大小，即一页占多少个内容，这里指一页占多少字符
        SetScrollInfo(m_hwnd, SB_HORZ, &si, TRUE);
        break;
    case WM_VSCROLL:
        // Get all the vertical scroll bar information
        si.cbSize = sizeof(si);
        si.fMask = SIF_ALL;
        GetScrollInfo(m_hwnd, SB_VERT, &si);

        // Save the position for comparison later on
        iVertPos = si.nPos;
        switch (LOWORD(wParam))
        {
        case SB_TOP:
            si.nPos = si.nMin;
            break;
        case SB_BOTTOM:
            si.nPos = si.nMax;
            break;
        case SB_LINEUP:
            si.nPos -= 1;
            break;
        case SB_LINEDOWN:
            si.nPos += 1;
            break;
        case SB_PAGEUP:
            si.nPos -= si.nPage;
            break;
        case SB_PAGEDOWN:
            si.nPos += si.nPage;
            break;
        case SB_ENDSCROLL:
            // ignore
            break;
        case SB_THUMBTRACK:
            si.nPos = si.nTrackPos;
            break;
        case SB_THUMBPOSITION:
            // ignore
            break;
        default:
            break;
        }
        // Set the position and then retrieve it. Due to adjustments
        // by Windows it may not be the same as the value set.

        si.fMask = SIF_POS;
        SetScrollInfo(m_hwnd, SB_VERT, &si, TRUE);
        GetScrollInfo(m_hwnd, SB_VERT, &si);

        // If the position has changed, scroll the window and update it
        if (si.nPos != iVertPos)
        {
            ScrollWindow(m_hwnd, 0, cyChar * (iVertPos - si.nPos), NULL, NULL);
            UpdateWindow(m_hwnd);
        }
        break;
    case WM_HSCROLL:
        // Get all the vertical scroll bar information
        si.cbSize = sizeof(si);
        si.fMask = SIF_ALL;
        GetScrollInfo(m_hwnd, SB_HORZ, &si);

        // Save the position for comparison later on
        iHorzPos = si.nPos;
        switch (LOWORD(wParam))
        {
        case SB_LINELEFT:
            si.nPos -= 1;
            break;
        case SB_LINERIGHT:
            si.nPos += 1;
            break;
        case SB_PAGELEFT:
            si.nPos -= si.nPage;
            break;
        case SB_PAGERIGHT:
            si.nPos += si.nPage;
            break;
        case SB_ENDSCROLL:
            break;
        case SB_THUMBTRACK:
            si.nPos = si.nTrackPos;
            break;
        case SB_THUMBPOSITION:
            break;
        case SB_LEFT:
            break;
        case SB_RIGHT:
            break;
        default:
            break;
        }
        // Set the position and then retrieve it. Due to adjustments
        // by Windows it may not be the same as the value set.
        // 如果 si 超出了范围，SetScrollInfo 时，会被系统修正
        // 所以重新获取修正后的值，并且判断是否被修正，如果修正了，则将窗口滚动到正确位置
        si.fMask = SIF_POS;
        SetScrollInfo(m_hwnd, SB_HORZ, &si, TRUE);
        GetScrollInfo(m_hwnd, SB_HORZ, &si);

        // If the position has changed, scroll the window
        if (si.nPos != iHorzPos)
        {
            ScrollWindow(m_hwnd, cxChar * (iHorzPos - si.nPos), 0, NULL, NULL);
        }
        break;
    case WM_PAINT:
        // 这里只重新绘制 invalid rectangle 部分
        hdc = BeginPaint(m_hwnd, &ps);

        // Get vertical scroll bar position
        si.cbSize = sizeof(si);
        si.fMask = SIF_POS;
        GetScrollInfo(m_hwnd, SB_VERT, &si);
        iVertPos = si.nPos;

        // Get horizontal scroll bar position
        GetScrollInfo(m_hwnd, SB_HORZ, &si);
        iHorzPos = si.nPos;

        // Find painting limits
        iPaintBeg = max(0, iVertPos + ps.rcPaint.top / cyChar);
        iPaintEnd = min(NUMLINES - 1, iVertPos + ps.rcPaint.bottom / cxChar);

        for (int i = iPaintBeg; i < iPaintEnd; i++)
        {
            x = cxChar * (1 - iHorzPos);
            y = cyChar * (i - iVertPos);

            TextOut(hdc, x, y, sysmetrics[i].szLable, lstrlen(sysmetrics[i].szLable));
            TextOut(hdc, x + cxCaps * 22, y, sysmetrics[i].szDesc, lstrlen(sysmetrics[i].szDesc));

            SetTextAlign(hdc, TA_RIGHT | TA_TOP);
            TextOut(hdc, x + cxCaps * 22 + cxChar * 40, y, szBuffer, wsprintf(szBuffer, L"%5d", GetSystemMetrics(sysmetrics[i].index)));
            SetTextAlign(hdc, TA_LEFT | TA_TOP);
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

INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    UNREFERENCED_PARAMETER(lParam);
    switch (message)
    {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;

    case WM_COMMAND:
        if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
        {
            EndDialog(hDlg, LOWORD(wParam));
            return (INT_PTR)TRUE;
        }
        break;
    }
    return (INT_PTR)FALSE;
}