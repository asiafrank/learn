// MyPlayer.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "MyPlayer.h"

#define MAX_LOADSTRING 100

// Global Variables:
HINSTANCE hInst;                                // current instance
WCHAR szTitle[MAX_LOADSTRING];                  // The title bar text
WCHAR szWindowClass[MAX_LOADSTRING];            // the main window class name
int  clientX, clientY;

// Forward declarations of functions included in this code module:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

// D3D objects
IDXGISwapChain      *swapchain;  // the pointer to the swap chain interface
ID3D11Device        *dev;        // the pointer to our Direct3D device interface
ID3D11DeviceContext *devcon;     // the pointer to our Direct3D device context
ID3D11RenderTargetView *backbuffer;

void InitD3D(HWND canvasHwnd);   // sets up and initializes Direct3D
void RenderFrame(void);
void CleanD3D(void);             // closes Direct3D and releases memory


int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // TODO: Place code here.

    // Initialize global strings
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_MYPLAYER, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Perform application initialization:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_MYPLAYER));

    MSG msg;

    // Main message loop:
    while (true)
    {
        if (PeekMessage(&msg, NULL, 0, 0, PM_REMOVE))
        {
            if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
            {
                TranslateMessage(&msg);
                DispatchMessage(&msg);
            }

            if (msg.message == WM_QUIT)
                break;
        }
        else
        {
            // D3D frame
            RenderFrame();
            Sleep(100);
        }
    }

    CleanD3D();
    return (int) msg.wParam;
}

//
//  FUNCTION: MyRegisterClass()
//
//  PURPOSE: Registers the window class.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;

    wcex.cbSize = sizeof(WNDCLASSEX);

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_MYPLAYER));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_MYPLAYER);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    return RegisterClassExW(&wcex);
}

//
//   FUNCTION: InitInstance(HINSTANCE, int)
//
//   PURPOSE: Saves instance handle and creates main window
//
//   COMMENTS:
//
//        In this function, we save the instance handle in a global variable and
//        create and display the main program window.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // Store instance handle in our global variable

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
      CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE:  Processes messages for the main window.
//
//  WM_COMMAND  - process the application menu
//  WM_PAINT    - Paint the main window
//  WM_DESTROY  - post a quit message and return
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    static HBRUSH hBrushStatic = CreateSolidBrush(RGB(0, 0, 0));
    static LPCWSTR canvasClsname = L"playCanvas";
    static HWND playCanvas;
    static HWND button;

    static int  cxChar, cyChar;

    static BOOL inited = FALSE;

    switch (message)
    {
    case WM_CREATE:
        cxChar = LOWORD(GetDialogBaseUnits());
        cyChar = HIWORD(GetDialogBaseUnits());
      
        button = CreateWindowExW(0, L"button", L"play",
                                 WS_CHILD | WS_VISIBLE |
                                 WS_TABSTOP | BS_PUSHBUTTON,
                                 0, 0, cxChar * 10, cyChar * 7 / 4,
                                 hWnd, (HMENU)ID_BTN_PLAY, hInst, NULL);

        break;
    case WM_SIZE:
        clientX = LOWORD(lParam);
        clientY = HIWORD(lParam);

        if (!inited) {
            playCanvas = CreateWindowExW(0, L"static", canvasClsname,
                WS_CHILD | WS_VISIBLE | WS_TABSTOP,
                0, cyChar * 2, clientX, clientY - (cyChar * 2),
                hWnd, NULL, hInst, NULL);
            InitD3D(playCanvas);
        }
        break;
    case WM_CTLCOLORSTATIC:
        {
            HDC hdcStatic = (HDC)wParam;
            SetBkColor(hdcStatic, RGB(30, 30, 30));
            return (LRESULT)hBrushStatic;
        }
        break;
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            // Parse the menu selections:
            switch (wmId)
            {
            case IDM_ABOUT:
                DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
                break;
            case IDM_EXIT:
                DestroyWindow(hWnd);
                break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            // TODO: Add any drawing code that uses hdc here...
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

// Message handler for about box.
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

// this function initializes and prepares Direct3D for use
void InitD3D(HWND canvasHwnd)
{
    //------------ Direct 3D initialization ----------------
    // create a struct to hold information about the swap chain
    DXGI_SWAP_CHAIN_DESC scd;

    // clear out the struct for use
    ZeroMemory(&scd, sizeof(DXGI_SWAP_CHAIN_DESC));

    // fill the swap chain description struct
    scd.BufferCount        = 1;                                // one back buffer
    scd.BufferDesc.Format  = DXGI_FORMAT_R8G8B8A8_UNORM;       // use 32-bit color
    scd.BufferDesc.Width   = clientX;                          // set the back buffer width
    scd.BufferDesc.Height  = clientY;                          // set the back buffer height
    scd.BufferUsage        = DXGI_USAGE_RENDER_TARGET_OUTPUT;  // how swap chain is to be used
    scd.OutputWindow       = canvasHwnd;                       // the window to be used
    scd.SampleDesc.Count   = 1;                                // how many multisamples
    scd.SampleDesc.Quality = 0;                                // multisample quality level
    scd.Windowed           = TRUE;                             // windowed/full-screen mode
    scd.Flags              = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH; // allow full-screen switching

    // create a device, device context and swap chain using the information in the scd struct
    D3D11CreateDeviceAndSwapChain(NULL,
                                  D3D_DRIVER_TYPE_HARDWARE, 
                                  NULL, 
                                  NULL, 
                                  NULL, 
                                  NULL, 
                                  D3D11_SDK_VERSION, 
                                  &scd, 
                                  &swapchain, 
                                  &dev, 
                                  NULL, 
                                  &devcon);

    //------------ Set the render target ----------------

    // get address of the back buffer
    ID3D11Texture2D *pBackBuffer;
    swapchain->GetBuffer(0, __uuidof(ID3D11Texture2D), (LPVOID*)&pBackBuffer);

    // use the back buffer address to create the render target
    dev->CreateRenderTargetView(pBackBuffer, NULL, &backbuffer);
    pBackBuffer->Release();

    // set the render target as the back buffer
    devcon->OMSetRenderTargets(1, &backbuffer, NULL);

    //------------ Set the viewport ----------------
    D3D11_VIEWPORT viewport;
    ZeroMemory(&viewport, sizeof(D3D11_VIEWPORT));

    viewport.TopLeftX = 0;
    viewport.TopLeftY = 0;
    viewport.Width    = clientX;
    viewport.Height   = clientY;

    devcon->RSSetViewports(1, &viewport);
}

// this is the function used to render a single frame
void RenderFrame(void)
{
    float clearColor[4] = { 0.0f, 0.2f, 0.4f, 1.0f };
    // clear the back buffer to a deep blue
    devcon->ClearRenderTargetView(backbuffer, clearColor);

    // do 3D rendering on the back buffer here
    
    
    // switch the back buffer and the front buffer
    swapchain->Present(0, 0);
}

// this is the function that cleans up Direct3D and COM
void CleanD3D(void)
{
    swapchain->SetFullscreenState(FALSE, NULL); // switch to windowed mode

    // close and release all existing COM objectss
    swapchain->Release();
    backbuffer->Release();
    dev->Release();
    devcon->Release();
}
