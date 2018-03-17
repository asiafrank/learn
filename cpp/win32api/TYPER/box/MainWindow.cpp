#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define BUFFER(x, y) *(pBuffer + y * cxBuffer + x)

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static DWORD dwCharSet = DEFAULT_CHARSET;
    static int cxChar, cyChar, cxClient, cyClient, cxBuffer, cyBuffer, xCaret, yCaret;
    static TCHAR *pBuffer = NULL;
    HDC hdc;
    int x, y, i;
    PAINTSTRUCT ps;
    TEXTMETRIC tm;

    switch (uMsg)
    {
    case WM_INPUTLANGCHANGE:
        dwCharSet = wParam;
        // fall through
    case WM_CREATE:
        hdc = GetDC(m_hwnd);
        SelectObject(hdc, CreateFont(0, 0, 0, 0, 0, 0, 0, 0, dwCharSet, 0, 0, 0, FIXED_PITCH, NULL));

        GetTextMetrics(hdc, &tm);
        cxChar = tm.tmAveCharWidth;
        cyChar = tm.tmHeight;

        DeleteObject(SelectObject(hdc, GetStockObject(SYSTEM_FONT)));
        ReleaseDC(m_hwnd, hdc);
        // fall through
    case WM_SIZE:
        // obtain window size in pixels
        if (uMsg == WM_SIZE)
        {
            cxClient = LOWORD(lParam);
            cyClient = HIWORD(lParam);
        }
        // calculate window size in characters
        cxBuffer = max(1, cxClient / cxChar);
        cyBuffer = max(1, cyClient / cyChar);

        // allocate memory for buffer and clear it
        if (pBuffer != NULL)
            free(pBuffer);

        pBuffer = (TCHAR *)malloc(cxBuffer * cyBuffer * sizeof(TCHAR));

        for (y = 0; y < cyBuffer; y++)
            for (x = 0; x < cxBuffer; x++)
                BUFFER(x, y) = ' ';
        // set caret to upper left corner

        xCaret = 0;
        yCaret = 0;

        if (m_hwnd == GetFocus())
            SetCaretPos(xCaret * cxChar, yCaret * cyChar);

        InvalidateRect(m_hwnd, NULL, TRUE);
        break;
    case WM_SETFOCUS:
        // create and show the caret
        CreateCaret(m_hwnd, NULL, cxChar, cyChar);
        SetCaretPos(xCaret * cxChar, yCaret * cyChar);
        ShowCaret(m_hwnd);
        break;
    case WM_KILLFOCUS:
        // hide and destroy the caret
        HideCaret(m_hwnd);
        DestroyCaret();
        break;
    case WM_KEYDOWN:
        switch (wParam)
        {
        case VK_HOME: 
            xCaret = 0; 
            break;
        case VK_END: 
            xCaret = cxBuffer - 1; 
            break;
        case VK_PRIOR: 
            yCaret = 0; 
            break;
        case VK_NEXT: 
            yCaret = cyBuffer - 1; 
            break;
        case VK_LEFT: 
            xCaret = max(xCaret - 1, 0);
            break;
        case VK_RIGHT:
            xCaret = min(xCaret + 1, cxBuffer - 1);
            break;
        case VK_UP: 
            yCaret = max(yCaret - 1, 0);
            break;
        case VK_DOWN: 
            yCaret = min(yCaret + 1, cyBuffer - 1);
            break;
        case VK_DELETE:
            for (x = xCaret; x < cxBuffer - 1; x++)
                BUFFER(x, yCaret) = BUFFER(x + 1, yCaret); // 向后删除字符，即从 xCaret 开始后面的字符项前移动
            BUFFER(cxBuffer - 1, yCaret) = ' '; // 上面 for 循环相当于删了一个字符，所以这里在一行的最后补充一个空格字符
            HideCaret(m_hwnd);
            hdc = GetDC(m_hwnd);
            SelectObject(hdc, CreateFont(0, 0, 0, 0, 0, 0, 0, 0, dwCharSet, 0, 0, 0, FIXED_PITCH, NULL));
            TextOut(hdc, xCaret * cxChar, yCaret * cyChar, &BUFFER(xCaret, yCaret), cxBuffer - xCaret);
            DeleteObject(SelectObject(hdc, GetStockObject(SYSTEM_FONT)));
            ReleaseDC(m_hwnd, hdc);
            ShowCaret(m_hwnd);
            break;
        }
        SetCaretPos(xCaret * cxChar, yCaret * cyChar);
        break;
    case WM_CHAR:
        for (i = 0; i < (int)LOWORD(lParam); i++)
        {
            switch (wParam)
            {
            case '\b': // backspace
                if (xCaret > 0)
                {
                    xCaret--;
                    SendMessage(m_hwnd, WM_KEYDOWN, VK_DELETE, 1);
                }
                break;
            case '\t': // tab
                do
                {
                    SendMessage(m_hwnd, WM_CHAR, ' ', 1);
                } while (xCaret % 8 != 0);
                break;
            case '\n': // line feed
                if (++yCaret == cyBuffer)
                    yCaret = 0;
                break;
            case '\r': // carriage return
                xCaret = 0;
                if (++yCaret == cyBuffer)
                    yCaret = 0;
                break;
            case '\x1B': // escape
                for (y = 0; y < cyBuffer; y++)
                    for (x = 0; x < cxBuffer; x++)
                        BUFFER(x, y) = ' ';
                xCaret = 0;
                yCaret = 0;
                InvalidateRect(m_hwnd, NULL, FALSE);
                break;
            default: // character codes
                BUFFER(xCaret, yCaret) = (TCHAR)wParam;

                HideCaret(m_hwnd);
                hdc = GetDC(m_hwnd);
                SelectObject(hdc, CreateFont(0, 0, 0, 0, 0, 0, 0, 0, dwCharSet, 0, 0, 0, FIXED_PITCH, NULL));
                TextOut(hdc, xCaret * cxChar, yCaret * cyChar, &BUFFER(xCaret, yCaret), 1);
                DeleteObject(SelectObject(hdc, GetStockObject(SYSTEM_FONT)));
                ReleaseDC(m_hwnd, hdc);
                ShowCaret(m_hwnd);

                if (++xCaret == cxBuffer)
                {
                    xCaret = 0;
                    if (++yCaret == cyBuffer)
                        yCaret = 0;
                }
                break;
            }
        }
        SetCaretPos(xCaret * cxChar, yCaret * cyChar);
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        SelectObject(hdc, CreateFont(0, 0, 0, 0, 0, 0, 0, 0, dwCharSet, 0, 0, 0, FIXED_PITCH, NULL));
        for (y = 0; y < cyBuffer; y++)
            TextOut(hdc, 0, y * cyChar, &BUFFER(0, y), cxBuffer);
        DeleteObject(SelectObject(hdc, GetStockObject(SYSTEM_FONT)));
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
