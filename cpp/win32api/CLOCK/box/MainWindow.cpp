#include "stdafx.h"
#include "Resource.h"
#include "MainWindow.h"
#include "devcaps.h"

#define ID_TIMER 1
#define TWOPI (2 * 3.14159)

void SetIsotropic(HDC hdc, int cxClient, int cyClient)
{
    SetMapMode(hdc, MM_ISOTROPIC);
    SetWindowExtEx(hdc, 1000, 1000, NULL);
    SetViewportExtEx(hdc, cxClient / 2, -cyClient / 2, NULL); // 这里 y 设为负值原因是建立 y轴向上为正，向下为负的笛卡尔坐标系
    SetViewportOrgEx(hdc, cxClient / 2, cyClient / 2, NULL);  // 设置显示区域的中心（设备单位）为圆点
}

/*
公式（a 代表 弧度 = 2π * 角度 / 360）：
x' = x * cos (a) + y * sin (a)
y' = y * cos (a) - x * sin (a)

通过上述公式转换坐标点
*/
void RotatePoint(POINT pt[], int iNum, int iAngle)
{
    int i;
    POINT ptTemp;

    for (i = 0; i < iNum; i++)
    {
        ptTemp.x = (int)(pt[i].x * cos(TWOPI * iAngle / 360) + pt[i].y * sin(TWOPI * iAngle / 360));
        ptTemp.y = (int)(pt[i].y * cos(TWOPI * iAngle / 360) - pt[i].x * sin(TWOPI * iAngle / 360));

        pt[i] = ptTemp;
    }
}

void DrawClock(HDC hdc)
{
    int iAngle;
    POINT pt[3]; // 画小圆点，一个圆点需要四个点定位
    for (iAngle = 0; iAngle < 360; iAngle += 6)
    {
        pt[0].x = 0;
        pt[0].y = 900;

        RotatePoint(pt, 1, iAngle); // 通过角度更改 pt[0] 的坐标，找到对应角度圆点的圆心坐标

        pt[2].x = pt[2].y = iAngle % 5 ? 33 : 100; // 角度除5，得 1 则是小圆点（没有整除），得 0 则是大圆点（能够整除）
                                                   // 这里的 33 与 100 是圆点的直径，临时存放在 pt[2] 中

        pt[0].x -= pt[2].x / 2; // 通过 pt[2] 中存的直径，计算圆点的左上角 x 坐标
        pt[0].y -= pt[2].y / 2; // 通过 pt[2] 中存的直径，计算圆点的左上角 y 坐标

        pt[1].x = pt[0].x + pt[2].x; // 通过 pt[2] 中存的直径，计算圆点的右下角 x 坐标
        pt[1].y = pt[0].y + pt[2].y; // 通过 pt[2] 中存的直径，计算圆点的右下角 y 坐标

        SelectObject(hdc, GetStockObject(BLACK_BRUSH));
        Ellipse(hdc, pt[0].x, pt[0].y, pt[1].x, pt[1].y); // 调用函数画出圆点
    }
}

// 画出时分秒的指针
void DrawHands(HDC hdc, SYSTEMTIME * pst, BOOL fChange)
{
    static POINT pt[3][5] = {
        { { 0, -150 },{ 100, 0 },{ 0, 600 },{ -100, 0 },{ 0, -150 } }, // 时指针多边形坐标点
        { { 0, -200 },{ 50, 0 },{ 0, 800 },{ -50, 0 },{ 0, -200 } },   // 分指针多边形坐标点
        { { 0, 0 },{ 0, 0 },{ 0, 0 },{ 0, 0 },{ 0, 800 } }             // 秒指针多边形坐标点
    };
    int i, iAngle[3];
    POINT ptTemp[3][5];

    iAngle[0] = (pst->wHour * 30) % 360 + pst->wMinute / 2; // 时 角度
    iAngle[1] = pst->wMinute * 6; // 分 角度
    iAngle[2] = pst->wSecond * 6; // 秒 角度

    memcpy(ptTemp, pt, sizeof(pt));
    for (i = fChange ? 0 : 2; i < 3; i++) // fChange 表示如果 分或时变化，则重画所有指针，否则只需画秒指针即可
    {
        RotatePoint(ptTemp[i], 5, iAngle[i]); // 通过角度旋转多边形各个坐标点
        Polyline(hdc, ptTemp[i], 5);          // 根据坐标点画出多边形
    }
}

LRESULT MainWindow::HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    static int cxClient, cyClient;
    static SYSTEMTIME stPrevious;
    BOOL fChange;
    HDC hdc;
    PAINTSTRUCT ps;
    SYSTEMTIME st;

    switch (uMsg)
    {
    case WM_CREATE:
        SetTimer(m_hwnd, ID_TIMER, 1000, NULL);
        GetLocalTime(&st);
        stPrevious = st;
        break;
    case WM_SIZE:
        cxClient = LOWORD(lParam);
        cyClient = HIWORD(lParam);
        break;
    case WM_TIMER:
        GetLocalTime(&st);
        fChange = st.wHour != stPrevious.wHour || st.wMinute != stPrevious.wMinute;

        hdc = GetDC(m_hwnd);

        SetIsotropic(hdc, cxClient, cyClient);

        SelectObject(hdc, GetStockObject(WHITE_PEN));
        DrawHands(hdc, &stPrevious, fChange);

        SelectObject(hdc, GetStockObject(BLACK_PEN));
        DrawHands(hdc, &st, TRUE);
        ReleaseDC(m_hwnd, hdc);

        stPrevious = st;
        break;
    case WM_PAINT:
        hdc = BeginPaint(m_hwnd, &ps);

        SetIsotropic(hdc, cxClient, cyClient);

        DrawClock(hdc);
        DrawHands(hdc, &stPrevious, TRUE);

        EndPaint(m_hwnd, &ps);
        break;
    case WM_DESTROY:
        KillTimer(m_hwnd, ID_TIMER);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(m_hwnd, uMsg, wParam, lParam);
    }
    return 0;
}
