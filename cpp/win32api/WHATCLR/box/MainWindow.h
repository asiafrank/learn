// 主窗口定义
#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "BaseWindow.h"

class MainWindow : public BaseWindow<MainWindow>
{
public:
    PCWSTR  ClassName() const { return L"Sample Window Class"; }
    LRESULT HandleMessage(UINT uMsg, WPARAM wParam, LPARAM lParam);
};

#endif // !MAINWINDOW_H
