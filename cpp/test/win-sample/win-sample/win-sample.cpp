// win-sample.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "win-sample.h"

INT_PTR CALLBACK aboutDlgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam);
INT_PTR CALLBACK toolDlgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam);

const LPCWSTR g_szClassName = L"myWindowClass";
HWND g_hToolbar = NULL;

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        case WM_CREATE:
            g_hToolbar = CreateDialog(GetModuleHandle(NULL), MAKEINTRESOURCE(IDD_TOOLBAR), hwnd, toolDlgProc);
            if (g_hToolbar != NULL)
            {
                ShowWindow(g_hToolbar, SW_SHOW);
            }
            else
            {
                MessageBox(hwnd, L"CreateDialog returned NULL", L"Warning!", MB_OK | MB_ICONINFORMATION);
            }
            break;
        case WM_COMMAND:
            switch (LOWORD(wParam))
            {
                case ID_FILE_EXIT:
                    PostMessage(hwnd, WM_CLOSE, 0, 0);
                    break;
                case ID_STUFF_GO:
                    break;
                case ID_HELP_ABOUT:
                    {
                        int ret = DialogBox(GetModuleHandle(NULL), MAKEINTRESOURCE(IDD_ABOUT), hwnd, aboutDlgProc);
                        if (ret == IDOK)
                        {
                            MessageBox(hwnd, L"Dialog exited with IDOK.", L"Notice", MB_OK | MB_ICONINFORMATION);
                        }
                        else if (ret == IDCANCEL)
                        {
                            MessageBox(hwnd, L"Dialog failed!", L"Error", MB_OK | MB_ICONINFORMATION);
                        }
                    }
                    break;
                case ID_DIALOG_SHOW:
                    ShowWindow(g_hToolbar, SW_SHOW);
                    break;
                case ID_DIALOG_HIDE:
                    ShowWindow(g_hToolbar, SW_HIDE);
                    break;
                // Other menu commands...
            }
            break;
        case WM_LBUTTONDOWN:
        {
            wchar_t szFileName[MAX_PATH];
            HINSTANCE hInstance = GetModuleHandle(NULL);

            GetModuleFileName(hInstance, szFileName, MAX_PATH);
            MessageBox(hwnd, szFileName, L"This program is:", MB_OK | MB_ICONINFORMATION);
        }
        break;
        case WM_CLOSE:
            DestroyWindow(hwnd);
            break;
        case WM_DESTROY:
            DestroyWindow(g_hToolbar);
            PostQuitMessage(0);
            break;
        default:
            return DefWindowProc(hwnd, msg, wParam, lParam);
    }
}

INT_PTR CALLBACK aboutDlgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        case WM_INITDIALOG:
            return TRUE;
        case WM_COMMAND:
            switch (LOWORD(wParam))
            {
                case IDOK:
                    EndDialog(hwnd, IDOK);
                    break;
                case IDCANCEL:
                    EndDialog(hwnd, IDCANCEL);
                    break;
            }
            break;
        default:
            return FALSE;
    }
    return TRUE;
}

INT_PTR CALLBACK toolDlgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        case WM_COMMAND:
            switch (LOWORD(wParam))
            {
                case IDC_PRESS:
                    MessageBox(hwnd, L"Hi!", L"This is a message", MB_OK | MB_ICONEXCLAMATION);
                    break;
                case IDC_OTHER:
                    MessageBox(hwnd, L"Bye!", L"This is a message", MB_OK | MB_ICONEXCLAMATION);
                    break;
                default:
                    break;
            }
            break;
        default:
            return FALSE;
    }
    return TRUE;
}

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
    _In_opt_ HINSTANCE hPrevInstance,
    _In_ LPWSTR    lpCmdLine,
    _In_ int       nCmdShow)
{
    WNDCLASSEX wc;
    HWND hwnd;
    MSG msg;

    // Step 1: Registering the Window Class
    wc.cbSize = sizeof(WNDCLASSEX);
    wc.style = 0;
    wc.lpfnWndProc = WndProc;
    wc.cbClsExtra = 0;
    wc.cbWndExtra = 0;
    wc.hInstance = hInstance;
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    wc.lpszMenuName = MAKEINTRESOURCE(IDR_MYMENU);
    wc.lpszClassName = g_szClassName;
    wc.hIcon = LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_MYICON));
    wc.hIconSm = (HICON)LoadImage(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_MYICON), IMAGE_ICON, 16, 16, 0);

    if (!RegisterClassEx(&wc))
    {
        MessageBox(NULL, L"Window Registration Failed!", L"Error!", MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

    // Step 2: Creating the Window
    hwnd = CreateWindowEx(
        0,
        g_szClassName,
        L"The title of my window",
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT, 240, 120,
        NULL, NULL, hInstance, NULL);

    if (hwnd == NULL)
    {
        MessageBox(NULL, L"Window Creation Failed!", L"Error!", MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    // Step 3: The Message Loop
    while (GetMessage(&msg, NULL, 0, 0) > 0)
    {
        if (!IsDialogMessage(g_hToolbar, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }
    return msg.wParam;
}