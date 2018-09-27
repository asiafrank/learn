#pragma once

#include "stdafx.h"

#include <string>
#include <memory>

#include "TransferContext.h"
#include "TransferClient.h"

using namespace std;

class App
{
public:
    App(HINSTANCE hInstance);
    App(const App& rhs) = delete; // disable copy
    App& operator=(const App& rhs) = delete; // disable copy
    ~App();
public:
    static App* getApp();

    HINSTANCE getAppInst()const;
    HWND      getHWND()const;

    HRESULT initialize();
    int Run();

    float     AspectRatio()const;
    LRESULT MsgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam);
private:
    void InitializeDPIScale(ID2D1Factory *pFactory)
    {
        FLOAT dpiX, dpiY;

        pFactory->GetDesktopDpi(&dpiX, &dpiY);

        dpiScaleX_ = dpiX / 96.0f;
        dpiScaleY_ = dpiY / 96.0f;
    }

    template <typename T>
    float PixelsToDipsX(T x)
    {
        return static_cast<float>(x) / dpiScaleX_;
    }

    template <typename T>
    float PixelsToDipsY(T y)
    {
        return static_cast<float>(y) / dpiScaleY_;
    }

    // timer process
    void TimerProc();

    // Initialize device-independent resources.
    HRESULT createDeviceIndependentResources();

    // Initialize device-dependent resources.
    HRESULT createDeviceResources();

    // Release device-dependent resource.
    void discardDeviceResources();

    // Draw content.
    HRESULT onRender();

    void onResize(UINT width, UINT height);

    HRESULT onCommand(WPARAM wParam);

private:
    static App* pApp;

    HINSTANCE appInst = nullptr; // application instance handle
    HWND      hwnd = nullptr;    // main window handles
    int clientWidth = 500;       // WM_SIZE 触发后获取的窗口宽度
    int clientHeight = 400;      // WM_SIZE 触发后获取的窗口高度

    std::wstring appTitle = L"App";

    TCHAR fileNameBuf[260];       // buffer for file name
    LPWSTR fileName = nullptr;
private:     //---- DirectWrite ----

    // https://docs.microsoft.com/en-us/windows/desktop/learnwin32/dpi-and-device-independent-pixels
    float dpiScaleX_ = 1.0f;
    float dpiScaleY_ = 1.0f;
    ID2D1Factory* m_pDirect2dFactory;
    ID2D1HwndRenderTarget* m_pRenderTarget;
    ID2D1SolidColorBrush* m_pLightSlateGrayBrush;
    ID2D1SolidColorBrush* m_pCornflowerBlueBrush;
    ID2D1SolidColorBrush* m_pGreenBrush;

    //---- DirectWrite ----
    IDWriteFactory* pDWriteFactory_;
    IDWriteTextFormat* pTextFormat_;

    ID2D1SolidColorBrush* pBlackBrush_;
private:
    //---- button ----
    D2D1_RECT_F btnRect;
    ID2D1SolidColorBrush* m_pBtnBrush;
    std::wstring btnTitle = L"开始";
    bool enableBtnClick = false;
    POINTS mousePoint;
private:
    // context
    std::shared_ptr<tf::TransferContext> pCtx;
    // client
    string serverHost = "127.0.0.1";
    string serverPort = "6881";
    std::shared_ptr<tf::TransferClient> pClient;
    std::shared_ptr<asio::io_context> pIOCtx;
};