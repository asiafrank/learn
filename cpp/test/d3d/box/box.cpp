// Box.cpp : Defines the entry point for the application.
// Chapter 6 exercises 以 Box.cpp 为基础，做修改。

#include "stdafx.h"
#include "Box.h"

#include "../Common/d3dApp.h"
#include "../Common/MathHelper.h"
#include "../Common/UploadBuffer.h"

using Microsoft::WRL::ComPtr;
using namespace DirectX;
using namespace DirectX::PackedVector;

struct Vertex
{
    XMFLOAT3 Pos;
    XMFLOAT4 Color;
};

struct ObjectConstants
{
    XMFLOAT4X4 WorldViewProj = MathHelper::Identity4x4();
};

class BoxApp : public D3DApp
{
public:
    BoxApp(HINSTANCE hInstance);
    BoxApp(const BoxApp& rhs) = delete;
    BoxApp& operator=(const BoxApp& rhs) = delete;
    ~BoxApp();

    virtual bool Initialize() override;
private:
    virtual void OnResize() override;
    virtual void Update(const GameTimer& gt) override;
    virtual void Draw(const GameTimer& gt) override;
    void RenderUI();

    virtual void OnMouseDown(WPARAM btnState, int x, int y) override;
    virtual void OnMouseUp(WPARAM btnState, int x, int y) override;
    virtual void OnMouseMove(WPARAM btnState, int x, int y) override;

    void D2DInit();
    void BuildDescriptorHeaps();
    void BuildConstantBuffers();
    void BuildRootSignature();
    void BuildShadersAndInputLayout();
    void BuildBoxGeometry();
    void BuildPSO();

private:
    ComPtr<ID3D12RootSignature> mRootSignature = nullptr;
    ComPtr<ID3D12DescriptorHeap> mCbvHeap = nullptr; // constant buffer view heap

    std::unique_ptr<UploadBuffer<ObjectConstants>> mObjectCB = nullptr; // object constant buffer
    std::unique_ptr<MeshGeometry> mGeo = nullptr;

    ComPtr<ID3DBlob> mvsByteCode = nullptr;
    ComPtr<ID3DBlob> mpsByteCode = nullptr;

    std::vector<D3D12_INPUT_ELEMENT_DESC> mInputLayout;

    ComPtr<ID3D12PipelineState> mPSO = nullptr;

    XMFLOAT4X4 mWorld = MathHelper::Identity4x4();
    XMFLOAT4X4 mView = MathHelper::Identity4x4();
    XMFLOAT4X4 mProj = MathHelper::Identity4x4();

    // 这里的 mTheta 和 mPhi 是初始时，摄像机对准的角度
    float mTheta = 1.5f*XM_PI; // 左右角度
    float mPhi = XM_PIDIV4;    // 上下角度
    // 摄像机到目标原点的距离，也相当于屏幕横坐标的大小;
    // 控制半径，能让立方体放大缩小，半径越大，立方体越小
    float mRadius = 10.0f;

    POINT mLastMousePos;

    //---- for Direct2D -----
    ComPtr<ID3D11On12Device> md3d11On12Device;
    ComPtr<ID3D11DeviceContext> md3d11DeviceContext;
    ComPtr<ID2D1Factory3> md2dFactory;
    ComPtr<ID2D1Device2> md2dDevice;
    ComPtr<ID2D1DeviceContext2> md2dDeviceContext;
    ComPtr<IDWriteFactory> mdWriteFactory;

    ComPtr<ID3D11Resource> mWrappedBackBuffers[SwapChainBufferCount];
    ComPtr<ID2D1Bitmap1> md2dRenderTargets[SwapChainBufferCount];
    ComPtr<ID2D1SolidColorBrush> mtextBrush;
    ComPtr<IDWriteTextFormat> mtextFormat;
};

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
    _In_opt_ HINSTANCE hPrevInstance,
    _In_ LPWSTR    lpCmdLine,
    _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);
    // Enable run-time memory check for debug builds.
#if defined(DEBUG) | defined(_DEBUG)
    _CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
#endif

    try
    {
        BoxApp theApp(hInstance);
        if (!theApp.Initialize())
            return 0;

        return theApp.Run();
    }
    catch (DxException& e)
    {
        MessageBox(nullptr, e.ToString().c_str(), L"HR Failed", MB_OK);
        return 0;
    }
}

BoxApp::BoxApp(HINSTANCE hInstance) : D3DApp(hInstance)
{}

BoxApp::~BoxApp()
{}

bool BoxApp::Initialize()
{
    if (!D3DApp::Initialize())
        return false;

    // Reset the command list to prep for initialization commands.
    ThrowIfFailed(mCommandList->Reset(mDirectCmdListAlloc.Get(), nullptr));

    D2DInit();
    BuildDescriptorHeaps();
    BuildConstantBuffers();
    BuildRootSignature();
    BuildShadersAndInputLayout();
    BuildBoxGeometry();
    BuildPSO();

    // Execute the initialization commands.
    ThrowIfFailed(mCommandList->Close());
    ID3D12CommandList* cmdsLists[] = { mCommandList.Get() };
    mCommandQueue->ExecuteCommandLists((UINT)std::size(cmdsLists), cmdsLists);

    // Wait until initialization is complete.
    FlushCommandQueue();
    return true;
}

void BoxApp::D2DInit()
{
    //----Direct2D-----
    D2D1_FACTORY_OPTIONS d2dFactoryOptions = {};
    UINT dxgiFactoryFlags = 0;
    UINT d3d11DeviceFlags = D3D11_CREATE_DEVICE_BGRA_SUPPORT;

#if defined(_DEBUG)
    // Enable the debug layer (requires the Graphics Tools "optional feature").
    // NOTE: Enabling the debug layer after device creation will invalidate the active device.
    {
        ComPtr<ID3D12Debug> debugController;
        if (SUCCEEDED(D3D12GetDebugInterface(IID_PPV_ARGS(&debugController))))
        {
            debugController->EnableDebugLayer();

            // Enable additional debug layers.
            dxgiFactoryFlags |= DXGI_CREATE_FACTORY_DEBUG;
            d3d11DeviceFlags |= D3D11_CREATE_DEVICE_DEBUG;
            d2dFactoryOptions.debugLevel = D2D1_DEBUG_LEVEL_INFORMATION;
        }
    }
#endif

    /*
    消除 CCommandList::SetGraphicsRootDescriptorTable: Specified GPU Descriptor Handle... 日志
    https://github.com/Microsoft/DirectX-Graphics-Samples/issues/212
    这么粗暴地消除日志不知道对不对，但这只官方给出的方案，先照做。
    */
#if defined(_DEBUG)
    // Filter a debug error coming from the 11on12 layer.
    ComPtr<ID3D12InfoQueue> infoQueue;
    if (SUCCEEDED(md3dDevice->QueryInterface(IID_PPV_ARGS(&infoQueue))))
    {
        // Suppress whole categories of messages.
        //D3D12_MESSAGE_CATEGORY categories[] = {};

        // Suppress messages based on their severity level.
        D3D12_MESSAGE_SEVERITY severities[] =
        {
            D3D12_MESSAGE_SEVERITY_INFO,
        };

        // Suppress individual messages by their ID.
        D3D12_MESSAGE_ID denyIds[] =
        {
            // This occurs when there are uninitialized descriptors in a descriptor table, even when a
            // shader does not access the missing descriptors.
            D3D12_MESSAGE_ID_INVALID_DESCRIPTOR_HANDLE,
        };

        D3D12_INFO_QUEUE_FILTER filter = {};
        //filter.DenyList.NumCategories = _countof(categories);
        //filter.DenyList.pCategoryList = categories;
        filter.DenyList.NumSeverities = (UINT)std::size(severities);
        filter.DenyList.pSeverityList = severities;
        filter.DenyList.NumIDs = (UINT)std::size(denyIds);
        filter.DenyList.pIDList = denyIds;

        ThrowIfFailed(infoQueue->PushStorageFilter(&filter));
    }
#endif

    // Create an ID3D11On12Device
    ComPtr<ID3D11Device> d3d11Device;
    ThrowIfFailed(D3D11On12CreateDevice(
        md3dDevice.Get(),
        d3d11DeviceFlags,
        nullptr,
        0,
        reinterpret_cast<IUnknown**>(mCommandQueue.GetAddressOf()),
        1,
        0,
        &d3d11Device,
        &md3d11DeviceContext,
        nullptr
    ));

    // Query the 11On12 device from the 11 device.
    ThrowIfFailed(d3d11Device.As(&md3d11On12Device));

    {
        D2D1_DEVICE_CONTEXT_OPTIONS deviceOptions = D2D1_DEVICE_CONTEXT_OPTIONS_NONE;
        ThrowIfFailed(D2D1CreateFactory(D2D1_FACTORY_TYPE_SINGLE_THREADED,
            __uuidof(ID2D1Factory3),
            &d2dFactoryOptions,
            &md2dFactory));
        ComPtr<IDXGIDevice> dxgiDevice;
        ThrowIfFailed(md3d11On12Device.As(&dxgiDevice));
        ThrowIfFailed(md2dFactory->CreateDevice(dxgiDevice.Get(), &md2dDevice));
        ThrowIfFailed(md2dDevice->CreateDeviceContext(deviceOptions, &md2dDeviceContext));
        ThrowIfFailed(DWriteCreateFactory(DWRITE_FACTORY_TYPE_SHARED,
            __uuidof(IDWriteFactory),
            &mdWriteFactory));
    }

    // Query the desktop's dpi settings, which will be used to create
    // D2D's render targets.
    float dpiX;
    float dpiY;
    md2dFactory->GetDesktopDpi(&dpiX, &dpiY);
    D2D1_BITMAP_PROPERTIES1 bitmapProperties = D2D1::BitmapProperties1(
        D2D1_BITMAP_OPTIONS_TARGET | D2D1_BITMAP_OPTIONS_CANNOT_DRAW,
        D2D1::PixelFormat(DXGI_FORMAT_UNKNOWN, D2D1_ALPHA_MODE_PREMULTIPLIED),
        dpiX,
        dpiY
    );

    // Create frame resources.
    {
        // Create a RTV, D2D render target, and a command allocator for each frame.
        for (UINT n = 0; n < SwapChainBufferCount; n++)
        {
            // ThrowIfFailed(mSwapChain->GetBuffer(n, IID_PPV_ARGS(&mSwapChainBuffer[n]))); // 已被D3DApp初始化
            // md3dDevice->CreateRenderTargetView(mSwapChainBuffer[n].Get(), nullptr, rtvHandle);// 已被D3DApp初始化

            // Create a wrapped 11On12 resource of this back buffer. Since we are 
            // rendering all D3D12 content first and then all D2D content, we specify 
            // the In resource state as RENDER_TARGET - because D3D12 will have last 
            // used it in this state - and the Out resource state as PRESENT. When 
            // ReleaseWrappedResources() is called on the 11On12 device, the resource 
            // will be transitioned to the PRESENT state.
            D3D11_RESOURCE_FLAGS d3d11Flags = { D3D11_BIND_RENDER_TARGET };
            ThrowIfFailed(md3d11On12Device->CreateWrappedResource(
                mSwapChainBuffer[n].Get(),
                &d3d11Flags,
                D3D12_RESOURCE_STATE_RENDER_TARGET,
                D3D12_RESOURCE_STATE_PRESENT,
                IID_PPV_ARGS(&mWrappedBackBuffers[n])
            ));

            // Create a render target for D2D to draw directly to this back buffer.
            ComPtr<IDXGISurface> surface;
            ThrowIfFailed(mWrappedBackBuffers[n].As(&surface));
            ThrowIfFailed(md2dDeviceContext->CreateBitmapFromDxgiSurface(
                surface.Get(),
                &bitmapProperties,
                &md2dRenderTargets[n]
            ));
        }
    }

    // Create D2D/DWrite objects for rendering text.
    {
        ThrowIfFailed(md2dDeviceContext->CreateSolidColorBrush(
            D2D1::ColorF(D2D1::ColorF::Black),
            &mtextBrush));
        ThrowIfFailed(mdWriteFactory->CreateTextFormat(
            L"SimHei",
            NULL,
            DWRITE_FONT_WEIGHT_NORMAL,
            DWRITE_FONT_STYLE_NORMAL,
            DWRITE_FONT_STRETCH_NORMAL,
            50,
            L"en-us",
            &mtextFormat
        ));
        ThrowIfFailed(mtextFormat->SetTextAlignment(DWRITE_TEXT_ALIGNMENT_CENTER));
        ThrowIfFailed(mtextFormat->SetParagraphAlignment(DWRITE_PARAGRAPH_ALIGNMENT_CENTER));
    }
}

void BoxApp::OnResize()
{
    ::OutputDebugStringA((char*)"Resize invoked\n");
    /*
    由于加了 d2d 代码，在 Resize 时，mSwapChain—>ResizeBuffers 报错。
    原因是没有将相应直接或间接绑定的引用释放掉。
    https://docs.microsoft.com/en-us/windows/desktop/api/dxgi/nf-dxgi-idxgiswapchain-resizebuffers
    日志打印的错误： DXGI ERROR: IDXGISwapChain::ResizeBuffers: Swapchain cannot be resized unless all outstanding buffer references have been released. [ MISCELLANEOUS ERROR #19: ]

    相关问题参考: https://github.com/sharpdx/SharpDX/issues/903
    https://github.com/Microsoft/DirectX-Graphics-Samples/issues/48
    https://stackoverflow.com/questions/44155133/directx11-com-object-with-0-references-not-released
    */
    // D3DApp::OnResize();
    assert(md3dDevice);
    assert(mSwapChain);
    assert(mDirectCmdListAlloc);
        
    //====== 释放 d2d 和 d3d11 的资源, 使 mSwapChain->ResizeBuffers 成功
    if (md3d11On12Device)
    {
        for (UINT n = 0; n < SwapChainBufferCount; n++)
        {
            mWrappedBackBuffers[n].Reset();
            md2dRenderTargets[n].Reset();
        }
    }

    if (md2dDeviceContext)
    {
        md2dDeviceContext->SetTarget(nullptr);
    }

    if (md3d11DeviceContext)
    {
        ID3D11RenderTargetView* nullViews[] = { nullptr };
        md3d11DeviceContext->OMSetRenderTargets((UINT)std::size(nullViews), nullViews, nullptr);
        md3d11DeviceContext->Flush();
    }
    //======

    // Flush before changing any resources.
    FlushCommandQueue();

    ThrowIfFailed(mCommandList->Reset(mDirectCmdListAlloc.Get(), nullptr));

    // FIXME: 重新绑定 d2d
    // Release the previous resources we will be recreating.
    for (int i = 0; i < SwapChainBufferCount; ++i) {
        mSwapChainBuffer[i].Reset();
    }
    mDepthStencilBuffer.Reset();

    // Resize the swap chain.
    // 为了 D2D 进行修改
    ThrowIfFailed(mSwapChain->ResizeBuffers(
        SwapChainBufferCount,
        mClientWidth, mClientHeight,
        mBackBufferFormat,
        DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH));

    mCurrBackBuffer = 0;

    D2D1_BITMAP_PROPERTIES1 bitmapProperties;
    if (md2dFactory)
    {
        // Query the desktop's dpi settings, which will be used to create
        // D2D's render targets.
        float dpiX;
        float dpiY;
        md2dFactory->GetDesktopDpi(&dpiX, &dpiY);
        bitmapProperties = D2D1::BitmapProperties1(
            D2D1_BITMAP_OPTIONS_TARGET | D2D1_BITMAP_OPTIONS_CANNOT_DRAW,
            D2D1::PixelFormat(DXGI_FORMAT_UNKNOWN, D2D1_ALPHA_MODE_PREMULTIPLIED),
            dpiX,
            dpiY
        );
    }

    CD3DX12_CPU_DESCRIPTOR_HANDLE rtvHeapHandle(mRtvHeap->GetCPUDescriptorHandleForHeapStart());
    for (UINT i = 0; i < SwapChainBufferCount; i++)
    {
        ThrowIfFailed(mSwapChain->GetBuffer(i, IID_PPV_ARGS(&mSwapChainBuffer[i])));
        md3dDevice->CreateRenderTargetView(mSwapChainBuffer[i].Get(), nullptr, rtvHeapHandle);

        if (md3d11On12Device)
        {
            // D2D
            D3D11_RESOURCE_FLAGS d3d11Flags = { D3D11_BIND_RENDER_TARGET };
            ThrowIfFailed(md3d11On12Device->CreateWrappedResource(
                mSwapChainBuffer[i].Get(),
                &d3d11Flags,
                D3D12_RESOURCE_STATE_RENDER_TARGET,
                D3D12_RESOURCE_STATE_PRESENT,
                IID_PPV_ARGS(&mWrappedBackBuffers[i])
            ));
            ComPtr<IDXGISurface> surface;
            ThrowIfFailed(mWrappedBackBuffers[i].As(&surface));
            ThrowIfFailed(md2dDeviceContext->CreateBitmapFromDxgiSurface(
                surface.Get(),
                &bitmapProperties,
                &md2dRenderTargets[i]
            ));
        }

        rtvHeapHandle.Offset(1, mRtvDescriptorSize);
    }

    // Create the depth/stencil buffer and view.
    D3D12_RESOURCE_DESC depthStencilDesc;
    depthStencilDesc.Dimension = D3D12_RESOURCE_DIMENSION_TEXTURE2D;
    depthStencilDesc.Alignment = 0;
    depthStencilDesc.Width = mClientWidth;
    depthStencilDesc.Height = mClientHeight;
    depthStencilDesc.DepthOrArraySize = 1;
    depthStencilDesc.MipLevels = 1;

    // Correction 11/12/2016: SSAO chapter requires an SRV to the depth buffer to read from 
    // the depth buffer.  Therefore, because we need to create two views to the same resource:
    //   1. SRV format: DXGI_FORMAT_R24_UNORM_X8_TYPELESS
    //   2. DSV Format: DXGI_FORMAT_D24_UNORM_S8_UINT
    // we need to create the depth buffer resource with a typeless format.  
    depthStencilDesc.Format = DXGI_FORMAT_R24G8_TYPELESS;

    depthStencilDesc.SampleDesc.Count = m4xMsaaState ? 4 : 1;
    depthStencilDesc.SampleDesc.Quality = m4xMsaaState ? (m4xMsaaQuality - 1) : 0;
    depthStencilDesc.Layout = D3D12_TEXTURE_LAYOUT_UNKNOWN;
    depthStencilDesc.Flags = D3D12_RESOURCE_FLAG_ALLOW_DEPTH_STENCIL;

    D3D12_CLEAR_VALUE optClear;
    optClear.Format = mDepthStencilFormat;
    optClear.DepthStencil.Depth = 1.0f;
    optClear.DepthStencil.Stencil = 0;
    ThrowIfFailed(md3dDevice->CreateCommittedResource(
        &CD3DX12_HEAP_PROPERTIES(D3D12_HEAP_TYPE_DEFAULT),
        D3D12_HEAP_FLAG_NONE,
        &depthStencilDesc,
        D3D12_RESOURCE_STATE_COMMON,
        &optClear,
        IID_PPV_ARGS(mDepthStencilBuffer.GetAddressOf())));

    // Create descriptor to mip level 0 of entire resource using the format of the resource.
    D3D12_DEPTH_STENCIL_VIEW_DESC dsvDesc;
    dsvDesc.Flags = D3D12_DSV_FLAG_NONE;
    dsvDesc.ViewDimension = D3D12_DSV_DIMENSION_TEXTURE2D;
    dsvDesc.Format = mDepthStencilFormat;
    dsvDesc.Texture2D.MipSlice = 0;
    md3dDevice->CreateDepthStencilView(mDepthStencilBuffer.Get(), &dsvDesc, DepthStencilView());

    // Transition the resource from its initial state to be used as a depth buffer.
    mCommandList->ResourceBarrier(1, &CD3DX12_RESOURCE_BARRIER::Transition(mDepthStencilBuffer.Get(),
        D3D12_RESOURCE_STATE_COMMON, D3D12_RESOURCE_STATE_DEPTH_WRITE));

    // Execute the resize commands.
    ThrowIfFailed(mCommandList->Close());
    ID3D12CommandList* cmdsLists[] = { mCommandList.Get() };
    mCommandQueue->ExecuteCommandLists((UINT)std::size(cmdsLists), cmdsLists);

    // Wait until resize is complete.
    FlushCommandQueue();

    // Update the viewport transform to cover the client area.
    mScreenViewport.TopLeftX = 0;
    mScreenViewport.TopLeftY = 0;
    mScreenViewport.Width = static_cast<float>(mClientWidth);
    mScreenViewport.Height = static_cast<float>(mClientHeight);
    mScreenViewport.MinDepth = 0.0f;
    mScreenViewport.MaxDepth = 1.0f;

    mScissorRect = { 0, 0, mClientWidth, mClientHeight };

    // The window resized, so update the aspect ratio and recompute the projection
    // matrix.
    XMMATRIX P = XMMatrixPerspectiveFovLH(0.25f*MathHelper::Pi, AspectRatio(), 1.0f, 1000.0f);
    XMStoreFloat4x4(&mProj, P);
}

void BoxApp::Update(const GameTimer& gt)
{
    // Convert Spherical to Cartesian coordinates
    float x = mRadius * sinf(mPhi) * cosf(mTheta); // 0
    float z = mRadius * sinf(mPhi) * sinf(mTheta); // 5*(-1/sqrt(2)) = -3.53....
    float y = mRadius * cosf(mPhi);                // 5*(1/sqrt(2))

    XMVECTOR pos = XMVectorSet(x, y, z, 1.0f); // 摄像机位置
    XMVECTOR up = XMVectorSet(0.0f, 1.0f, 0.0f, 0.0f);

    XMMATRIX world = XMLoadFloat4x4(&mWorld);
    XMMATRIX proj = XMLoadFloat4x4(&mProj);

    XMVECTOR boxPos     = XMVectorSet(-2.0f, 0.0f, 0.0f, 0.0f); // 正面摄像机摆放时，box 坐标为 (-2,0,0)
    XMVECTOR pyramidPos = XMVectorSet(+2.0f, 0.0f, 0.0f, 0.0f); // 正面摄像机摆放时，pyramid 坐标为 (+2,0,0)

    // Build the view matrix.
    // Box 世界视图投影
    XMMATRIX view = XMMatrixLookAtLH(pos, boxPos, up);
    XMStoreFloat4x4(&mView, view);
    XMMATRIX worldViewProj = world * view * proj;

    // Update the constant buffer with the latest worldViewProj matrix.
    ObjectConstants objConstants;
    XMStoreFloat4x4(&objConstants.WorldViewProj, XMMatrixTranspose(worldViewProj));
    mObjectCB->CopyData(0, objConstants);

    // pyramid 世界视图投影
    view = XMMatrixLookAtLH(pos, pyramidPos, up);
    XMStoreFloat4x4(&mView, view);

    worldViewProj = world * view * proj;
    XMStoreFloat4x4(&objConstants.WorldViewProj, XMMatrixTranspose(worldViewProj));
    mObjectCB->CopyData(1, objConstants);
}

void BoxApp::Draw(const GameTimer& gt)
{
    // Reuse the memory associated with command recording.
    // We can only reset when the associated command lists
    // have finished execution on the GPU.
    ThrowIfFailed(mDirectCmdListAlloc->Reset());

    // A command list can be reset after it has been added to the command queue via ExecuteCommandList.
    // Reusing the command list reuses memory.
    ThrowIfFailed(mCommandList->Reset(mDirectCmdListAlloc.Get(), mPSO.Get()));

    mCommandList->RSSetViewports(1, &mScreenViewport);
    mCommandList->RSSetScissorRects(1, &mScissorRect);

    // Indicate a state transition on the resource usage.
    mCommandList->ResourceBarrier(1, &CD3DX12_RESOURCE_BARRIER::Transition(CurrentBackBuffer(),
        D3D12_RESOURCE_STATE_PRESENT, D3D12_RESOURCE_STATE_RENDER_TARGET));

    // Clear the back buffer and depth buffer.
    mCommandList->ClearRenderTargetView(CurrentBackBufferView(),
        Colors::LightSteelBlue, 0, nullptr);
    mCommandList->ClearDepthStencilView(DepthStencilView(),
        D3D12_CLEAR_FLAG_DEPTH | D3D12_CLEAR_FLAG_STENCIL, 1.0f, 0, 0, nullptr);

    // Specify the buffers we are going to render to.
    mCommandList->OMSetRenderTargets(1, &CurrentBackBufferView(), true, &DepthStencilView());

    ID3D12DescriptorHeap* descriptorHeaps[] = { mCbvHeap.Get() };
    mCommandList->SetDescriptorHeaps((UINT)std::size(descriptorHeaps), descriptorHeaps);
    mCommandList->SetGraphicsRootSignature(mRootSignature.Get());

    mCommandList->IASetVertexBuffers(0, 1, &mGeo->VertexBufferView());
    mCommandList->IASetIndexBuffer(&mGeo->IndexBufferView());
    mCommandList->IASetPrimitiveTopology(D3D11_PRIMITIVE_TOPOLOGY_TRIANGLELIST);

    UINT objCBByteSize = d3dUtil::CalcConstantBufferByteSize(sizeof(ObjectConstants));
    CD3DX12_GPU_DESCRIPTOR_HANDLE cbvHandle(mCbvHeap->GetGPUDescriptorHandleForHeapStart());

    mCommandList->SetGraphicsRootDescriptorTable(0, cbvHandle);
    SubmeshGeometry boxmesh = mGeo->DrawArgs["box"];
    mCommandList->DrawIndexedInstanced(
        boxmesh.IndexCount, 1,
        boxmesh.StartIndexLocation,
        boxmesh.BaseVertexLocation, 0);

    cbvHandle.Offset(1, mCbvSrvUavDescriptorSize);
    mCommandList->SetGraphicsRootDescriptorTable(0, cbvHandle);
    SubmeshGeometry pyramidmesh = mGeo->DrawArgs["pyramid"];
    mCommandList->DrawIndexedInstanced(
        pyramidmesh.IndexCount, 1,
        pyramidmesh.StartIndexLocation, 
        pyramidmesh.BaseVertexLocation, 0);

    // Indicate a state transition on the resource usage.
    mCommandList->ResourceBarrier(1, &CD3DX12_RESOURCE_BARRIER::Transition(CurrentBackBuffer(),
        D3D12_RESOURCE_STATE_RENDER_TARGET, D3D12_RESOURCE_STATE_PRESENT));

    // Done recording commands.
    ThrowIfFailed(mCommandList->Close());

    // Add the command list to the queue for execution.
    ID3D12CommandList* cmdsLists[] = { mCommandList.Get() };
    mCommandQueue->ExecuteCommandLists((UINT)std::size(cmdsLists), cmdsLists);

    RenderUI();

    // swap the back and front buffers
    ThrowIfFailed(mSwapChain->Present(0, 0));
    mCurrBackBuffer = (mCurrBackBuffer + 1) % SwapChainBufferCount;

    // Wait until frame commands are complete. This waiting is inefficient and is
    // done for simplicity. Later we will show how to organize our rendering code
    // so we do not have to wait per frame.
    FlushCommandQueue();
}

void BoxApp::RenderUI()
{
    D2D1_SIZE_F rtSize = md2dRenderTargets[mCurrBackBuffer]->GetSize();
    D2D1_RECT_F textRect = D2D1::RectF(0, 0, rtSize.width, rtSize.height);
    static const WCHAR text[] = L"11On12";

    // Acquire our wrapped render target resource for the current back buffer.
    md3d11On12Device->AcquireWrappedResources(mWrappedBackBuffers[mCurrBackBuffer].GetAddressOf(), 1);

    // Render text directly to the back buffer.
    md2dDeviceContext->SetTarget(md2dRenderTargets[mCurrBackBuffer].Get());
    md2dDeviceContext->BeginDraw();
    md2dDeviceContext->SetTransform(D2D1::Matrix3x2F::Identity());
    md2dDeviceContext->DrawTextW(
        text,
        _countof(text) - 1,
        mtextFormat.Get(),
        &textRect,
        mtextBrush.Get()
    );
    ThrowIfFailed(md2dDeviceContext->EndDraw());

    // Release our wrapped render target resource. Releasing 
    // transitions the back buffer resource to the state specified
    // as the OutState when the wrapped resource was created.
    md3d11On12Device->ReleaseWrappedResources(mWrappedBackBuffers[mCurrBackBuffer].GetAddressOf(), 1);

    // Flush to submit the 11 command list to the shared command queue.
    md3d11DeviceContext->Flush();
}

void BoxApp::OnMouseDown(WPARAM btnState, int x, int y)
{
    mLastMousePos.x = x;
    mLastMousePos.y = y;

    SetCapture(mhMainWnd);
}

void BoxApp::OnMouseUp(WPARAM btnState, int x, int y)
{
    ReleaseCapture();
}

void BoxApp::OnMouseMove(WPARAM btnState, int x, int y)
{
    if ((btnState & MK_LBUTTON) != 0)
    {
        // Make each pixel correspond to a quarter of a degree.
        float dx = XMConvertToRadians(0.25f*static_cast<float>(x - mLastMousePos.x));
        float dy = XMConvertToRadians(0.25f*static_cast<float>(y - mLastMousePos.y));

        // Update angles based on input to orbit camera around box.
        mTheta += dx;
        mPhi += dy;

        // Restrict the angle mPhi.
        mPhi = MathHelper::Clamp(mPhi, 0.1f, MathHelper::Pi - 0.1f);
    }
    else if ((btnState & MK_RBUTTON) != 0)
    {
        // Make each pixel correspond to 0.005 unit in the scene.
        float dx = 0.005f*static_cast<float>(x - mLastMousePos.x);
        float dy = 0.005f*static_cast<float>(y - mLastMousePos.y);

        // Update the camera radius based on input.
        mRadius += dx - dy;

        // Restrict the radius.
        mRadius = MathHelper::Clamp(mRadius, 3.0f, 15.0f);
    }

    mLastMousePos.x = x;
    mLastMousePos.y = y;
}

void BoxApp::BuildDescriptorHeaps()
{
    D3D12_DESCRIPTOR_HEAP_DESC cbvHeapDesc;
    cbvHeapDesc.NumDescriptors = 2;
    cbvHeapDesc.Type = D3D12_DESCRIPTOR_HEAP_TYPE_CBV_SRV_UAV;
    cbvHeapDesc.Flags = D3D12_DESCRIPTOR_HEAP_FLAG_SHADER_VISIBLE;
    cbvHeapDesc.NodeMask = 0;
    ThrowIfFailed(md3dDevice->CreateDescriptorHeap(&cbvHeapDesc, IID_PPV_ARGS(&mCbvHeap)));
}

void BoxApp::BuildConstantBuffers()
{
    mObjectCB = std::make_unique<UploadBuffer<ObjectConstants>>(md3dDevice.Get(), 2, true);

    UINT objCBByteSize = d3dUtil::CalcConstantBufferByteSize(sizeof(ObjectConstants));

    D3D12_GPU_VIRTUAL_ADDRESS cbAddress = mObjectCB->Resource()->GetGPUVirtualAddress();
    // Offset to the ith object constant buffer in the buffer.
    int boxCBufIndex = 0;
    cbAddress += boxCBufIndex * objCBByteSize;

    D3D12_CONSTANT_BUFFER_VIEW_DESC boxCbvDesc;
    boxCbvDesc.BufferLocation = cbAddress;
    boxCbvDesc.SizeInBytes = objCBByteSize;

    int pyramidCBufIndex = 1;
    cbAddress += pyramidCBufIndex * objCBByteSize;

    D3D12_CONSTANT_BUFFER_VIEW_DESC pyramidCbvDesc;
    pyramidCbvDesc.BufferLocation = cbAddress;
    pyramidCbvDesc.SizeInBytes = objCBByteSize;

    CD3DX12_CPU_DESCRIPTOR_HANDLE cpuHandle(mCbvHeap->GetCPUDescriptorHandleForHeapStart());
    md3dDevice->CreateConstantBufferView(&boxCbvDesc, cpuHandle);
    cpuHandle.Offset(1, mCbvSrvUavDescriptorSize);
    md3dDevice->CreateConstantBufferView(&pyramidCbvDesc, cpuHandle);
}

void BoxApp::BuildRootSignature()
{
    // Shader programs typically require resources as input (constant buffers,
    // textures, samplers). The root signature defines the resources the shader
    // programs expect. If we think of the shader programs as a function, and 
    // the input resources as function parameters, then the root signature can be
    // thought of as defining the function signature.

    // Root parameter can be a table, root descriptor or root constants.
    CD3DX12_ROOT_PARAMETER slotRootParameter[1];

    // Create a single descriptor table of CBVs.
    CD3DX12_DESCRIPTOR_RANGE cbvTable0;
    cbvTable0.Init(D3D12_DESCRIPTOR_RANGE_TYPE_CBV, 1, 0);
    slotRootParameter[0].InitAsDescriptorTable(1, &cbvTable0);

    // A root signature is an array of root parameters.
    CD3DX12_ROOT_SIGNATURE_DESC rootSigDesc(1, slotRootParameter, 0, nullptr,
        D3D12_ROOT_SIGNATURE_FLAG_ALLOW_INPUT_ASSEMBLER_INPUT_LAYOUT);

    // create a root signature with a single slot which points to a descriptor range 
    // consisting of a single constant buffer
    ComPtr<ID3DBlob> serializedRootSig = nullptr;
    ComPtr<ID3DBlob> errorBlob = nullptr;
    HRESULT hr = D3D12SerializeRootSignature(&rootSigDesc, D3D_ROOT_SIGNATURE_VERSION_1,
        serializedRootSig.GetAddressOf(), errorBlob.GetAddressOf());

    if (errorBlob != nullptr)
    {
        ::OutputDebugStringA((char*)errorBlob->GetBufferPointer());
    }
    ThrowIfFailed(hr);

    ThrowIfFailed(md3dDevice->CreateRootSignature(
        0,
        serializedRootSig->GetBufferPointer(),
        serializedRootSig->GetBufferSize(),
        IID_PPV_ARGS(&mRootSignature)));
}

void BoxApp::BuildShadersAndInputLayout()
{
    HRESULT hr = S_OK;

    mvsByteCode = d3dUtil::CompileShader(L"Shaders\\color.hlsl", nullptr, "VS", "vs_5_0");
    mpsByteCode = d3dUtil::CompileShader(L"Shaders\\color.hlsl", nullptr, "PS", "ps_5_0");

    mInputLayout = {
        { "POSITION", 0, DXGI_FORMAT_R32G32B32_FLOAT, 0, 0, D3D12_INPUT_CLASSIFICATION_PER_VERTEX_DATA, 0 },
        { "COLOR", 0, DXGI_FORMAT_R32G32B32A32_FLOAT, 0, 12, D3D12_INPUT_CLASSIFICATION_PER_VERTEX_DATA, 0 }
    };
}

void BoxApp::BuildBoxGeometry()
{
    // DirectX 是左手坐标系，z轴负在前，正在后
    std::array<Vertex, 13> vertices = {
        // 立方体
        Vertex({ XMFLOAT3(-1.0f, -1.0f, -1.0f), XMFLOAT4(Colors::White) }),  // 0: (-1,-1,-1)
        Vertex({ XMFLOAT3(-1.0f, +1.0f, -1.0f), XMFLOAT4(Colors::Black) }),  // 1: (-1, +1, -1)
        Vertex({ XMFLOAT3(+1.0f, +1.0f, -1.0f), XMFLOAT4(Colors::Red) }),    // 2: (+1, +1, -1)
        Vertex({ XMFLOAT3(+1.0f, -1.0f, -1.0f), XMFLOAT4(Colors::Green) }),  // 3: (+1, -1, -1)
        Vertex({ XMFLOAT3(-1.0f, -1.0f, +1.0f), XMFLOAT4(Colors::Blue) }),   // 4: (-1, -1, +1)
        Vertex({ XMFLOAT3(-1.0f, +1.0f, +1.0f), XMFLOAT4(Colors::Yellow) }), // 5: (-1, +1, +1)
        Vertex({ XMFLOAT3(+1.0f, +1.0f, +1.0f), XMFLOAT4(Colors::Cyan) }),   // 6: (+1, +1, +1)
        Vertex({ XMFLOAT3(+1.0f, -1.0f, +1.0f), XMFLOAT4(Colors::Magenta) }),// 7: (+1, -1, +1)

        // 金字塔
        Vertex({ XMFLOAT3(-1.0f, 0.0f, -1.0f), XMFLOAT4(Colors::Green) }),  // 0: (-1,0,-1)
        Vertex({ XMFLOAT3(+1.0f, 0.0f, -1.0f), XMFLOAT4(Colors::Green) }),  // 1: (1,0,-1)
        Vertex({ XMFLOAT3(+1.0f, 0.0f, +1.0f), XMFLOAT4(Colors::Green) }),  // 2: (1,0,1)
        Vertex({ XMFLOAT3(-1.0f, 0.0f, +1.0f), XMFLOAT4(Colors::Green) }),  // 3: (-1,0,1)
        Vertex({ XMFLOAT3(0.0f, +1.5f, 0.0f),  XMFLOAT4(Colors::Red) }),    // 4: (0,1.5,0)
    };

    std::array<std::uint16_t, 54> indices = {
        // 立方体
        // 前面
        0, 1, 2,
        0, 2, 3,

        // 背面
        4, 6, 5,
        4, 7, 6,

        // 左侧面
        4, 5, 1,
        4, 1, 0,

        // 右侧面
        3, 2, 6,
        3, 6, 7,

        // 上面
        1, 5, 6,
        1, 6, 2,

        // 下面
        4, 0, 3,
        4, 3, 7, // indexCount: 36

        // 金字塔
        // 底面
        0, 1, 2, 
        0, 2, 3,

        // 侧面1
        0, 4, 1,

        // 侧面2
        1, 4, 2,

        // 侧面3
        2, 4, 3,

        // 侧面4
        3, 4, 0 // indexCount: 18
    };

    const UINT boxIndexCount = 36;
    const UINT boxStartIndexLocation = 0;
    const UINT boxBaseVertexLocation = 0;

    const UINT pyramidIndexCount = 18;
    const UINT pyramidStartIndexLocation = 36;
    const UINT pyramidBaseVertexLocation = 8;

    const UINT vbByteSize = (UINT)vertices.size() * sizeof(Vertex);
    const UINT ibByteSize = (UINT)indices.size() * sizeof(std::uint16_t);

    mGeo = std::make_unique<MeshGeometry>();
    mGeo->Name = "Geo";

    ThrowIfFailed(D3DCreateBlob(vbByteSize, &mGeo->VertexBufferCPU));
    CopyMemory(mGeo->VertexBufferCPU->GetBufferPointer(), vertices.data(), vbByteSize);

    ThrowIfFailed(D3DCreateBlob(ibByteSize, &mGeo->IndexBufferCPU));
    CopyMemory(mGeo->IndexBufferCPU->GetBufferPointer(), indices.data(), ibByteSize);

    mGeo->VertexBufferGPU = d3dUtil::CreateDefaultBuffer(md3dDevice.Get(),
        mCommandList.Get(), vertices.data(), vbByteSize, mGeo->VertexBufferUploader);

    mGeo->IndexBufferGPU = d3dUtil::CreateDefaultBuffer(md3dDevice.Get(),
        mCommandList.Get(), indices.data(), ibByteSize, mGeo->IndexBufferUploader);

    mGeo->VertexByteStride = sizeof(Vertex);
    mGeo->VertexBufferByteSize = vbByteSize;
    mGeo->IndexFormat = DXGI_FORMAT_R16_UINT;
    mGeo->IndexBufferByteSize = ibByteSize;

    SubmeshGeometry boxmesh;
    boxmesh.IndexCount = boxIndexCount;
    boxmesh.StartIndexLocation = boxStartIndexLocation;
    boxmesh.BaseVertexLocation = boxBaseVertexLocation;

    SubmeshGeometry pyramidmesh;
    pyramidmesh.IndexCount = pyramidIndexCount;
    pyramidmesh.StartIndexLocation = pyramidStartIndexLocation;
    pyramidmesh.BaseVertexLocation = pyramidBaseVertexLocation;

    mGeo->DrawArgs["box"] = boxmesh;
    mGeo->DrawArgs["pyramid"] = pyramidmesh;
}

void BoxApp::BuildPSO()
{
    D3D12_GRAPHICS_PIPELINE_STATE_DESC psoDesc;
    ZeroMemory(&psoDesc, sizeof(D3D12_GRAPHICS_PIPELINE_STATE_DESC));
    psoDesc.InputLayout = { mInputLayout.data(), (UINT)mInputLayout.size() };
    psoDesc.pRootSignature = mRootSignature.Get();
    psoDesc.VS = {
        reinterpret_cast<BYTE*>(mvsByteCode->GetBufferPointer()),
        mvsByteCode->GetBufferSize()
    };
    psoDesc.PS = {
        reinterpret_cast<BYTE*>(mpsByteCode->GetBufferPointer()),
        mpsByteCode->GetBufferSize()
    };

    CD3DX12_RASTERIZER_DESC rsDesc(D3D12_DEFAULT);
    psoDesc.RasterizerState = rsDesc;
    psoDesc.BlendState = CD3DX12_BLEND_DESC(D3D12_DEFAULT);
    psoDesc.DepthStencilState = CD3DX12_DEPTH_STENCIL_DESC(D3D12_DEFAULT);
    psoDesc.SampleMask = UINT_MAX;
    psoDesc.PrimitiveTopologyType = D3D12_PRIMITIVE_TOPOLOGY_TYPE_TRIANGLE;
    psoDesc.NumRenderTargets = 1;
    psoDesc.RTVFormats[0] = mBackBufferFormat;
    psoDesc.SampleDesc.Count = m4xMsaaState ? 4 : 1;
    psoDesc.SampleDesc.Quality = m4xMsaaState ? (m4xMsaaQuality - 1) : 0;
    psoDesc.DSVFormat = mDepthStencilFormat;
    ThrowIfFailed(md3dDevice->CreateGraphicsPipelineState(&psoDesc, IID_PPV_ARGS(&mPSO)));
}