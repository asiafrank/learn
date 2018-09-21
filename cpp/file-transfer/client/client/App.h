#pragma once

#include "stdafx.h"

#include <string>
#include <memory>

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
    HWND      hwnd = nullptr; // main window handles
    int clientWidth = 500; // WM_SIZE 触发后获取的窗口宽度
    int clientHeight = 400; // WM_SIZE 触发后获取的窗口高度

    std::wstring appTitle = L"App";

    TCHAR fileNameBuf[260];       // buffer for file name
    LPWSTR fileName = nullptr;
private:     //---- DirectWrite ----
    ID2D1Factory* m_pDirect2dFactory;
    ID2D1HwndRenderTarget* m_pRenderTarget;
    ID2D1SolidColorBrush* m_pLightSlateGrayBrush;
    ID2D1SolidColorBrush* m_pCornflowerBlueBrush;

    //---- DirectWrite ----
    IDWriteFactory* pDWriteFactory_;
    IDWriteTextFormat* pTextFormat_;

    ID2D1SolidColorBrush* pBlackBrush_;
};