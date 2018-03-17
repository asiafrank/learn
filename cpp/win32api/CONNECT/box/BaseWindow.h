#ifndef BASEWINDOW_H
#define BASEWINDOW_H

#include <windows.h>

template <class DERIVED_TYPE>
class BaseWindow {
public:
    BaseWindow() : m_hwnd(NULL) {}

    BOOL Create(
        PCWSTR lpWindowName,
        DWORD dwStyle,
        DWORD dwExStyle = 0,
        int x = CW_USEDEFAULT,
        int y = CW_USEDEFAULT,
        int nWidth = CW_USEDEFAULT,
        int nHeight = CW_USEDEFAULT,
        HWND hWndParent = 0,
        HMENU hMenu = 0)
    {
        m_hInst = GetModuleHandle(NULL);

        WNDCLASS wc = { 0 };
        wc.lpfnWndProc = DERIVED_TYPE::WindowProc;
        wc.hInstance = m_hInst;
        wc.lpszClassName = ClassName();
        wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
        wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
        wc.hCursor = LoadCursor(NULL, IDC_ARROW);
        wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);

        RegisterClass(&wc);

        // When this function is called, it sends the WM_NCCREATE and WM_CREATE to WindowProc
        // then pThis->m_hwnd = hwnd;
        // finally the m_hwnd is not NULL, it will return TRUE
        CreateWindowEx(
            dwExStyle,
            ClassName(),
            lpWindowName,
            dwStyle,
            x, y, nWidth, nHeight,
            hWndParent,
            hMenu,
            GetModuleHandle(NULL),
            this);
        return (m_hwnd ? TRUE : FALSE);
    }

    HWND Window() const { return m_hwnd; }

    static LRESULT CALLBACK WindowProc(
        HWND hwnd,
        UINT uMsg,
        WPARAM wParam,
        LPARAM lParam)
    {
        DERIVED_TYPE* pThis = NULL;
        if (uMsg == WM_NCCREATE)
        {
            CREATESTRUCT* pCreate = (CREATESTRUCT*)lParam;
            pThis = (DERIVED_TYPE*)pCreate->lpCreateParams;
            SetWindowLongPtr(hwnd, GWLP_USERDATA, (LONG_PTR)pThis);
        }
        else
        {
            pThis = (DERIVED_TYPE*)GetWindowLongPtr(hwnd, GWLP_USERDATA);
        }

        if (pThis)
        {
            pThis->m_hwnd = hwnd;
            return pThis->HandleMessage(uMsg, wParam, lParam);
        }
        else
        {
            return DefWindowProc(hwnd, uMsg, wParam, lParam);
        }
    }
protected:
    HINSTANCE m_hInst;
    HWND m_hwnd;

    virtual PCWSTR ClassName() const = 0;
    virtual LRESULT HandleMessage(
        UINT uMsg,
        WPARAM wParam,
        LPARAM lParam) = 0;
};

#endif // !BASEWINDOW_H

