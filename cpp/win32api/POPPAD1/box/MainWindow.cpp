#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define ID_EDIT 1

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static HWND hwndEdit;

    switch (uMsg)
    {
    case WM_CREATE:
        hwndEdit = CreateWindow(TEXT("edit"), NULL,
            WS_CHILD | WS_VISIBLE | WS_HSCROLL | WS_VSCROLL |
            WS_BORDER | ES_LEFT | ES_MULTILINE |
            ES_AUTOHSCROLL | ES_AUTOVSCROLL,
            0, 0, 0, 0, m_hwnd, (HMENU) ID_EDIT, ((LPCREATESTRUCT) lParam)->hInstance, NULL);
        break;
    case WM_SETFOCUS:
        SetFocus(hwndEdit);
        break;
    case WM_SIZE:
        MoveWindow(hwndEdit, 0, 0, LOWORD(lParam), HIWORD(lParam), TRUE);
        break;
    case WM_COMMAND:
        if (LOWORD(wParam) == ID_EDIT)
            if (HIWORD(wParam) == EN_ERRSPACE || HIWORD(wParam) == EN_MAXTEXT)
                MessageBox(m_hwnd, TEXT("Edit control out of space."), TEXT("PopPad1"), MB_OK | MB_ICONSTOP);
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
