#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFileDialog>
#include <QPixmap>
#include <QImageReader>
#include <QAction>

#include <QDebug>

#include "mainwindow.h"
#include "DirListTab.h"
#include "ImgTab.h"
#include "CustomTabStyle.h"

MainWindow::MainWindow(QWidget *parent)
    : QWidget(parent),
      config(new Config)
{
    iniUI(); //界面创建与布局
    iniSignalSlots(); //信号与槽的关联
    setWindowTitle("Form created mannually");
}

MainWindow::~MainWindow()
{

}

// 退出清空资源
void MainWindow::exit()
{
    delete config;
}

void MainWindow::iniUI()
{
    config->init();
    int width = config->getWidth();
    int height = config->getHeight();
    QRect rec = config->getRect();

    QSize newSize(width, height);
    this->setWindowTitle("图片查看器");
    this->setGeometry(QStyle::alignedRect(
                          Qt::LeftToRight,
                          Qt::AlignCenter,
                          newSize,
                          rec
                      ));

    tabWidget = new QTabWidget;
    tabWidget->addTab(new DirListTab(tabWidget), "列表");
    tabWidget->addTab(new ImgTab(tabWidget), "图像");
    // 隐藏 tabBar，使用 自定义 Button（样式自定） 来调用 QTabWidget->setCurrentIndex 来切换.
    tabWidget->tabBar()->hide();

    listBtn = new QPushButton;
    listBtn->setText("列表");

    imageBtn = new QPushButton;
    imageBtn->setText("图像");

    QVBoxLayout *tabBarLayout = new QVBoxLayout;
    tabBarLayout->addWidget(listBtn);
    tabBarLayout->addWidget(imageBtn);
    tabBarLayout->setDirection(QBoxLayout::Direction::TopToBottom);
    tabBarLayout->setSpacing(0);
    tabBarLayout->setContentsMargins(0, 0, 0, 0);
    tabBarLayout->setAlignment(Qt::AlignTop);

    QHBoxLayout *mainLayout = new QHBoxLayout;
    mainLayout->addLayout(tabBarLayout);
    mainLayout->addWidget(tabWidget);
    mainLayout->setSpacing(0);
    mainLayout->setContentsMargins(0, 0, 0, 0);

    // setStyleSheet("background:white");
    setLayout(mainLayout);
}

void MainWindow::iniSignalSlots()
{
    connect(listBtn, &QPushButton::clicked, this, [this]{setCurrentIndex(0);});
    connect(imageBtn, &QPushButton::clicked, this, [this]{setCurrentIndex(1);});
}

void MainWindow::setCurrentIndex(int index)
{
    tabWidget->setCurrentIndex(index);
}
