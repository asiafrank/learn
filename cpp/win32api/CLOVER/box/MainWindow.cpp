#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define TWO_PI (2.0 * 3.14159)

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{  
    static HRGN hRgnClip;
    static int cxClient, cyClient;
    double fAngle, fRadius;
    HCURSOR hCursor;
    HDC hdc;
    HRGN hRgnTemp[6];
    int i;
    PAINTSTRUCT ps;

    switch (uMsg)
    {
    case WM_CREATE:
        break;
    case WM_SIZE:
        cxClient = LOWORD(lParam);
        cyClient = HIWORD(lParam);

        hCursor = SetCursor(LoadCursor(NULL, IDC_WAIT));
        ShowCursor(TRUE);

        if (hRgnClip)
            DeleteObject(hRgnClip);

        hRgnTemp[0] = CreateEllipticRgn(0, cyClient / 3, cxClient / 2, 2 * cyClient / 3); // 左椭圆
        hRgnTemp[1] = CreateEllipticRgn(cxClient / 2, cyClient / 3, cxClient, 2 * cyClient / 3); // 右椭圆
        hRgnTemp[2] = CreateEllipticRgn(cxClient / 3, 0, 2 * cxClient / 3, cyClient / 2); // 上椭圆
        hRgnTemp[3] = CreateEllipticRgn(cxClient / 3, cyClient / 2, 2 * cxClient / 3, cyClient); // 下椭圆
        hRgnTemp[4] = CreateRectRgn(0, 0, 1, 1);
        hRgnTemp[5] = CreateRectRgn(0, 0, 1, 1);
        hRgnClip = CreateRectRgn(0, 0, 1, 1);

        CombineRgn(hRgnTemp[4], hRgnTemp[0], hRgnTemp[1], RGN_OR); // 左右椭圆的并集 A
        CombineRgn(hRgnTemp[5], hRgnTemp[2], hRgnTemp[3], RGN_OR); // 上下椭圆的并集 B
        CombineRgn(hRgnClip, hRgnTemp[4], hRgnTemp[5], RGN_XOR); // 上面得出的并集 A 与 B 的并集除去公共部分

        // 计算完图形后，将中间计算过程的对象删除
        for (size_t i = 0; i < 6; i++)
        {
            DeleteObject(hRgnTemp[i]);
        }

        SetCursor(hCursor);
        ShowCursor(FALSE);
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);
        SetViewportOrgEx(hdc, cxClient / 2, cyClient / 2, NULL); // 将坐标系定在中间，便于从中间画射线
        SelectClipRgn(hdc, hRgnClip); // 剪裁区域使用设备坐标
        fRadius = _hypot(cxClient / 2.0, cyClient / 2.0);
        for (fAngle = 0.0; fAngle < TWO_PI; fAngle += TWO_PI / 360)
        {
            MoveToEx(hdc, 0, 0, NULL);
            LineTo(hdc, (int)(fRadius * cos(fAngle) + 0.5), (int)(-fRadius * sin(fAngle) + 0.5));
        }
        EndPaint(m_hwnd, &ps);
        break;
        DeleteObject(hRgnClip);
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
