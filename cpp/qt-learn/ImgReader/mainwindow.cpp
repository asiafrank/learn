#include "mainwindow.h"
#include <QHBoxLayout>
#include <QFileDialog>
#include <QPixmap>
#include <QImageReader>
#include <QDebug>

#include "DirListTab.h"
#include "ImgTab.h"
#include "CustomTabStyle.h"

MainWindow::MainWindow(QWidget *parent)
    : QTabWidget(parent)
{
    iniUI(); //界面创建与布局
    iniSignalSlots(); //信号与槽的关联
    setWindowTitle("Form created mannually");
}

MainWindow::~MainWindow()
{

}

void MainWindow::iniUI()
{
    this->addTab(new DirListTab(this), "列表");
    this->addTab(new ImgTab(this), "图片");
    this->setTabPosition(TabPosition::West);
}

void MainWindow::iniSignalSlots()
{
}
