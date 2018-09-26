
#include "stdafx.h"
#include "App.h"
#include "Resource.h"

INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

LRESULT CALLBACK
MainWndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    // Forward hwnd on because we can get messages (e.g., WM_CREATE)
    // before CreateWindow returns, and thus before mhMainWnd is valid.
    return App::getApp()->MsgProc(hwnd, msg, wParam, lParam);
}

App*  App::pApp;
App* App::getApp()
{
    return pApp;
}

App::App(HINSTANCE hInstance)
    : appInst(hInstance),
    hwnd(nullptr),
    m_pDirect2dFactory(nullptr),
    m_pRenderTarget(nullptr),
    m_pLightSlateGrayBrush(nullptr),
    m_pCornflowerBlueBrush(nullptr)
{
    // Only one App can be constructed.
    pApp = this;
}

App::~App()
{
    SafeRelease(&m_pDirect2dFactory);
    SafeRelease(&m_pRenderTarget);
    SafeRelease(&m_pLightSlateGrayBrush);
    SafeRelease(&m_pCornflowerBlueBrush);
}

HINSTANCE App::getAppInst()const
{
    return appInst;
}

HWND App::getHWND()const
{
    return hwnd;
}

float App::AspectRatio()const
{
    return static_cast<float>(clientWidth) / clientHeight;
}

HRESULT App::initialize()
{
    HRESULT hr;
    // Initialize device-indpendent resources, such
    // as the Direct2D factory.
    hr = createDeviceIndependentResources();

    if (SUCCEEDED(hr))
    {
        WNDCLASS wc;
        wc.style = CS_HREDRAW | CS_VREDRAW;
        wc.lpfnWndProc = MainWndProc;
        wc.cbClsExtra = 0;
        wc.cbWndExtra = 0;
        wc.hInstance = appInst;
        wc.hIcon = LoadIcon(0, IDI_APPLICATION);
        wc.hCursor = LoadCursor(0, IDC_ARROW);
        wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
        wc.lpszMenuName = MAKEINTRESOURCE(IDC_CLIENT);
        wc.lpszClassName = L"MainWnd";

        if (!RegisterClass(&wc))
        {
            MessageBox(0, L"RegisterClass Failed.", 0, 0);
            return false;
        }

        InitializeDPIScale(m_pDirect2dFactory);

        int width = (int)ceil(PixelsToDipsX(clientWidth));
        int height = (int)ceil(PixelsToDipsY(clientHeight));

        hwnd = CreateWindow(
            L"MainWnd", 
            appTitle.c_str(),
            WS_OVERLAPPEDWINDOW,
            CW_USEDEFAULT,
            CW_USEDEFAULT,
            width,
            height,
            0, 0, appInst, 0);

        if (!hwnd)
        {
            MessageBox(0, L"CreateWindow Failed.", 0, 0);
            return E_FAIL;
        }

        ShowWindow(hwnd, SW_SHOWNORMAL);
        UpdateWindow(hwnd);
    }

    return hr;
}

int App::Run()
{
    MSG msg = { 0 };

    while (msg.message != WM_QUIT)
    {
        // If there are Window messages then process them.
        if (PeekMessage(&msg, 0, 0, 0, PM_REMOVE))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int)msg.wParam;
}

// Initialize device - independent resources.
HRESULT App::createDeviceIndependentResources()
{
    HRESULT hr = S_OK;

    // Create a Direct2D factory.
    hr = D2D1CreateFactory(D2D1_FACTORY_TYPE_SINGLE_THREADED, &m_pDirect2dFactory);

    if (SUCCEEDED(hr))
    {
        hr = DWriteCreateFactory(
            DWRITE_FACTORY_TYPE_SHARED,
            __uuidof(IDWriteFactory),
            reinterpret_cast<IUnknown**>(&pDWriteFactory_));
    }

    if (SUCCEEDED(hr))
    {
        hr = pDWriteFactory_->CreateTextFormat(
            L"SimHei",                // Font family name.
            NULL,                     // Font collection (NULL sets it to use the system font collection).
            DWRITE_FONT_WEIGHT_REGULAR,
            DWRITE_FONT_STYLE_NORMAL,
            DWRITE_FONT_STRETCH_NORMAL,
            14.0f,
            L"ch-cn",
            &pTextFormat_
        );
    }

    // Center align (horizontally) the text.
    if (SUCCEEDED(hr))
    {
        hr = pTextFormat_->SetTextAlignment(DWRITE_TEXT_ALIGNMENT_LEADING);
    }

    if (SUCCEEDED(hr))
    {
        hr = pTextFormat_->SetParagraphAlignment(DWRITE_PARAGRAPH_ALIGNMENT_CENTER);
    }

    if (SUCCEEDED(hr))
    {
        hr = pTextFormat_->SetWordWrapping(DWRITE_WORD_WRAPPING_NO_WRAP);
    }

    return hr;
}

// Initialize device-dependent resources.
HRESULT App::createDeviceResources()
{
    HRESULT hr = S_OK;
    if (!m_pRenderTarget) {
        RECT rc;
        GetClientRect(hwnd, &rc);

        D2D1_SIZE_U size = D2D1::SizeU(
            rc.right - rc.left,
            rc.bottom - rc.top
        );

        // Create a Direct2D render target.
        hr = m_pDirect2dFactory->CreateHwndRenderTarget(
            D2D1::RenderTargetProperties(),
            D2D1::HwndRenderTargetProperties(hwnd, size),
            &m_pRenderTarget);

        if (SUCCEEDED(hr))
        {
            // Create a gray brush.
            hr = m_pRenderTarget->CreateSolidColorBrush(
                D2D1::ColorF(D2D1::ColorF::LightSlateGray),
                &m_pLightSlateGrayBrush
            );
        }
        if (SUCCEEDED(hr))
        {
            // Create a blue brush.
            hr = m_pRenderTarget->CreateSolidColorBrush(
                D2D1::ColorF(D2D1::ColorF::CornflowerBlue),
                &m_pCornflowerBlueBrush
            );
        }

        // Create a black brush.
        if (SUCCEEDED(hr))
        {
            hr = m_pRenderTarget->CreateSolidColorBrush(
                D2D1::ColorF(D2D1::ColorF::Black),
                &pBlackBrush_
            );
        }

        if (SUCCEEDED(hr))
        {
            hr = m_pRenderTarget->CreateSolidColorBrush(
                D2D1::ColorF(D2D1::ColorF::LightGreen), 
                &m_pGreenBrush
            );
        }

        if (SUCCEEDED(hr))
        {
            hr = m_pRenderTarget->CreateSolidColorBrush(
                D2D1::ColorF(D2D1::ColorF::LightCyan),
                &m_pBtnBrush
            );
        }
    }
    return hr;
}

// Release device-dependent resource.
void App::discardDeviceResources()
{
    SafeRelease(&m_pRenderTarget);
    SafeRelease(&m_pLightSlateGrayBrush);
    SafeRelease(&m_pCornflowerBlueBrush);
    SafeRelease(&pBlackBrush_);
    SafeRelease(&m_pGreenBrush);
    SafeRelease(&m_pBtnBrush);
}

// Draw content.
HRESULT App::onRender()
{
    HRESULT hr = S_OK;

    hr = createDeviceResources();

    if (SUCCEEDED(hr))
    {
        m_pRenderTarget->BeginDraw();
        m_pRenderTarget->SetTransform(D2D1::Matrix3x2F::Identity());
        m_pRenderTarget->Clear(D2D1::ColorF(D2D1::ColorF::White));

        D2D1_SIZE_F rtSize = m_pRenderTarget->GetSize();

        // Draw a grid background.
        int width = static_cast<int>(rtSize.width);
        int height = static_cast<int>(rtSize.height);

        for (int x = 0; x < width; x += 10)
        {
            m_pRenderTarget->DrawLine(
                D2D1::Point2F(static_cast<FLOAT>(x), 0.0f),
                D2D1::Point2F(static_cast<FLOAT>(x), rtSize.height),
                m_pLightSlateGrayBrush,
                0.5f
            );
        }

        for (int y = 0; y < height; y += 10)
        {
            m_pRenderTarget->DrawLine(
                D2D1::Point2F(0.0f, static_cast<FLOAT>(y)),
                D2D1::Point2F(rtSize.width, static_cast<FLOAT>(y)),
                m_pLightSlateGrayBrush,
                0.5f
            );
        }

        // Draw two rectangles.
        D2D1_RECT_F rectangle1 = D2D1::RectF(
            rtSize.width / 2 - 50.0f,
            rtSize.height / 2 - 50.0f,
            rtSize.width / 2 + 50.0f,
            rtSize.height / 2 + 50.0f
        );

        D2D1_RECT_F rectangle2 = D2D1::RectF(
            rtSize.width / 2 - 100.0f,
            rtSize.height / 2 - 100.0f,
            rtSize.width / 2 + 100.0f,
            rtSize.height / 2 + 100.0f
        );

        // Draw a filled rectangle.
        m_pRenderTarget->FillRectangle(&rectangle1, m_pLightSlateGrayBrush);

        // Draw the outline of a rectangle.
        m_pRenderTarget->DrawRectangle(&rectangle2, m_pCornflowerBlueBrush);

        //----------- custom draw ----------
        // Draw Text
        if (fileName)
        {
            RECT rc;
            GetClientRect(hwnd, &rc);

            D2D1_RECT_F layoutRect = D2D1::RectF(
                static_cast<FLOAT>(rc.left) / dpiScaleX_,
                static_cast<FLOAT>(rc.top) / dpiScaleY_,
                static_cast<FLOAT>(rc.right - rc.left) / dpiScaleX_,
                static_cast<FLOAT>(rc.bottom - rc.top) / dpiScaleY_
            );

            // 文字所在方框
            D2D1_RECT_F textRect = D2D1::RectF(
                0, 0,
                layoutRect.right, 
                2 * 14 // 字体的两倍
            );
            m_pRenderTarget->DrawRectangle(&textRect, m_pCornflowerBlueBrush);

            // 进度条方框
            if (pCtx)
            {
                D2D1_RECT_F progressRect = D2D1::RectF(
                    textRect.left, textRect.top,
                    textRect.right * pCtx->getPercent() / 100,
                    textRect.bottom
                );

                m_pRenderTarget->FillRectangle(&progressRect, m_pGreenBrush);
            }

            m_pRenderTarget->DrawText(
                fileName,            // The string to render.
                lstrlen(fileName),   // The string's length.
                pTextFormat_, // The text format.
                textRect,    // The region of the window where the text will be rendered.
                pBlackBrush_  // The brush used to draw the text.
            );

            // 按钮
            size_t length = btnTitle.length();
            btnRect = D2D1::RectF(
                0, 4 * 14,
                length * 14,
                6 * 14
            );

            m_pRenderTarget->FillRectangle(&btnRect, m_pBtnBrush);

            m_pRenderTarget->DrawText(
                btnTitle.c_str(),            // The string to render.
                lstrlen(btnTitle.c_str()),   // The string's length.
                pTextFormat_, // The text format.
                btnRect,     // The region of the window where the text will be rendered.
                pBlackBrush_  // The brush used to draw the text.
            );
        }
        
        hr = m_pRenderTarget->EndDraw();
    }

    if (hr == D2DERR_RECREATE_TARGET)
    {
        hr = S_OK;
        discardDeviceResources();
    }

    return hr;
}

void App::onResize(UINT width, UINT height)
{
    if (m_pRenderTarget)
    {
        // Note: This method can fail, but it's okay to ignore the
        // error here, because the error will be returned again
        // the next time EndDraw is called.
        m_pRenderTarget->Resize(D2D1::SizeU(width, height));
    }
}


LRESULT App::MsgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    LRESULT result = 0;
    bool wasHandled = false;

    switch (msg)
    {
    case WM_CREATE:
    {
        // 建立1秒间隔的定时器
        HRESULT hr = SetTimer(hwnd, IDT_PROGRESS_TIMER, 1000, (TIMERPROC) nullptr);
        if (FAILED(hr))
        {
            MessageBox(NULL, L"This program requires Windows NT!",
                appTitle.c_str(), MB_ICONERROR);
        }
    }
    break;
    case WM_TIMER: // 定时器消息处理
    {
        switch (wParam)
        {
        case IDT_PROGRESS_TIMER:
            TimerProc();
            result = 0;
            wasHandled = true;
            InvalidateRect(hwnd, nullptr, TRUE); // repaint
        }
    }
    break;
    case WM_SIZE:
    {
        // Save the new client area dimensions.
        clientWidth = LOWORD(lParam);
        clientHeight = HIWORD(lParam);
        onResize(clientWidth, clientHeight);
        result = 0;
        wasHandled = true;
    }
    break;
    case WM_COMMAND:
    {
        onCommand(wParam);
        result = 0;
        wasHandled = true;
    }
    break;
    case WM_PAINT:
    {
        onRender();
        ValidateRect(hwnd, NULL);
        result = 0;
        wasHandled = true;
    }
    break;
    case WM_LBUTTONUP: // 鼠标点击
    {
        if (enableBtnClick)
        {
            mousePoint = MAKEPOINTS(lParam);
            float dipX = PixelsToDipsX(mousePoint.x);
            float dipY = PixelsToDipsY(mousePoint.y);

            // 判断 click 范围是否在 btn 范围内
            if (dipX > btnRect.left
                && dipX < btnRect.right
                && dipY > btnRect.top
                && dipY < btnRect.bottom)
            {
                if (pCtx)
                {
                    pCtx->setState(tf::State::Running);
                    // TODO: transfer file to server
                    // 使用 uint8_t 接收二进制
                }
                OutputDebugStringW(L"click valid");
                enableBtnClick = false;
            }
        }
    }
    break;
    case WM_DESTROY:
    {
        KillTimer(hwnd, IDT_PROGRESS_TIMER);
        PostQuitMessage(0);
        result = 1;
        wasHandled = true;
    }
    break;
    }

    if (!wasHandled)
    {
        result = DefWindowProc(hwnd, msg, wParam, lParam);
    }

    return result;
}

void App::TimerProc()
{
    // 计算进度条
    if (!pCtx) return;

    if (pCtx->getState() == tf::State::Running)
        pCtx->setPercent(pCtx->getPercent() + 10);

    if (pCtx->getPercent() >= 100.0) {
        pCtx->setState(tf::State::Done);
        OutputDebugStringW(L"context done");
    }
}

HRESULT App::onCommand(WPARAM wParam)
{
    int wmId = LOWORD(wParam);
    // Parse the menu selections:
    switch (wmId)
    {
    case IDM_ABOUT:
        DialogBox(appInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hwnd, About);
        break;
    case IDM_EXIT:
        DestroyWindow(hwnd);
        break;
    case IDM_OPEN: // open file
    {
        OPENFILENAME ofn;       // common dialog box structure

        // Initialize OPENFILENAME
        fileName = nullptr;
        ZeroMemory(&ofn, sizeof(ofn));
        ofn.lStructSize = sizeof(ofn);
        ofn.hwndOwner = hwnd;
        ofn.lpstrFile = fileNameBuf;
        // Set lpstrFile[0] to '\0' so that GetOpenFileName does not 
        // use the contents of szFile to initialize itself.
        ofn.lpstrFile[0] = '\0';
        ofn.nMaxFile = sizeof(fileNameBuf);
        ofn.lpstrFilter = L"All\0*.*\0Text\0*.TXT\0";
        ofn.nFilterIndex = 1;
        ofn.lpstrFileTitle = NULL;
        ofn.nMaxFileTitle = 0;
        ofn.lpstrInitialDir = NULL;
        ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;
        if (GetOpenFileName(&ofn))
        {
            fileName = ofn.lpstrFile;
            pCtx = make_shared<tf::TransferContext>(fileName);
            enableBtnClick = true;

            OutputDebugStringW(L"Open File dialog success!");
            RECT rc;
            GetClientRect(hwnd, &rc);
            InvalidateRect(hwnd, &rc, TRUE);
        }
        else
        {
            OutputDebugStringW(L"Open File dialog failed!");
        }
    }
    break;
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