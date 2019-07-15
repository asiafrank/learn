#include "mainwindow.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFileDialog>
#include <QPixmap>
#include <QImageReader>
#include <QDebug>

#include "DirListTab.h"
#include "ImgTab.h"
#include "CustomTabStyle.h"

MainWindow::MainWindow(QWidget *parent)
    : QWidget(parent)
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
    tabWidget = new QTabWidget;
    tabWidget->addTab(new DirListTab(tabWidget), "列表");
    tabWidget->addTab(new ImgTab(tabWidget), "图像");
    // TODO: 隐藏 tabBar，使用 自定义 Button（样式自定） 来调用 QTabWidget->setCurrentIndex 来切换.
    tabWidget->tabBar()->hide();

    QPushButton *listBtn = new QPushButton;
    listBtn->setText("列表");
    QPushButton *imageBtn = new QPushButton;
    imageBtn->setText("图像");

    QVBoxLayout *tabBarLayout = new QVBoxLayout;
    tabBarLayout->addWidget(listBtn);
    tabBarLayout->addWidget(imageBtn);

    QHBoxLayout *mainLayout = new QHBoxLayout;
    mainLayout->addLayout(tabBarLayout);
    mainLayout->addWidget(tabWidget);

    setLayout(mainLayout);
}

void MainWindow::iniSignalSlots()
{
}
