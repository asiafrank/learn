#include "mainwindow.h"
#include <QHBoxLayout>
#include <QFileDialog>
#include <QPixmap>
#include <QImageReader>
#include <QDebug>

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

void MainWindow::on_chkBoxUnder(bool checked)
{
    QFont font = txtEdit->font();
    font.setUnderline(checked);
    txtEdit->setFont(font);
}

void MainWindow::on_chkBoxItalic(bool checked)
{

}

void MainWindow::on_chkBoxBold(bool checked)
{

}

void MainWindow::setTextFontColor()
{

}

void MainWindow::openDir()
{
    QString dlgTitle="选择一个文件"; //对话框标题
    QString dirName = QFileDialog::getExistingDirectory(this, dlgTitle, *currentDir);
    if (!dirName.isEmpty()) {
        currentDir->clear();
        currentDir->append(dirName);
        fileListTree->setRootIndex(fileSystemModel->index(dirName));
    }
}

void MainWindow::treeDoubleClicked(const QModelIndex &index)
{
    if (!index.isValid()) {
        return;
    }

    QFileInfo fileInfo = fileSystemModel->fileInfo(index);
    if (fileInfo.isDir()) {
        QString filePath = fileInfo.filePath();
        fileListTree->setRootIndex(fileSystemModel->index(filePath));
        return;
    }

    if (fileInfo.isFile()) {
        if (fileInfo.suffix() == "png" || fileInfo.suffix() == "jpg") {
            QString path = fileInfo.path();
            QString fullFileName = fileInfo.path() + "/" + fileInfo.fileName();
            QImageReader reader(fullFileName);
            reader.setAutoTransform(true);

            const QImage image = reader.read();
            imageLabel->setPixmap(QPixmap::fromImage(image));
            imageLabel->show();
            return;
        }
    }
}

void MainWindow::iniUI()
{
    //创建 Underline, Italic, Bold 3 个CheckBox，并水平布局
    chkBoxUnder = new QCheckBox(tr("Underline"));
    chkBoxItalic = new QCheckBox(tr("Italic"));
    chkBoxBold = new QCheckBox(tr("Bold"));

    QHBoxLayout *HLay1 = new QHBoxLayout;
    HLay1->addWidget(chkBoxUnder);
    HLay1->addWidget(chkBoxItalic);
    HLay1->addWidget(chkBoxBold);

    //创建 Black, Red, Blue 3 个RadioButton，并水平布局
    rBtnBlack = new QRadioButton(tr("Black"));
    rBtnBlack->setChecked(true);
    rBtnRed = new QRadioButton(tr("Red"));
    rBtnBlue = new QRadioButton(tr("Blue"));

    QHBoxLayout *HLay2=new QHBoxLayout;
    HLay2->addWidget(rBtnBlack);
    HLay2->addWidget(rBtnRed);
    HLay2->addWidget(rBtnBlue);

    //创建确定, 取消, 退出3 个 PushButton, 并水平布局
    btnOK = new QPushButton(tr("确定"));
    btnCancel = new QPushButton(tr("取消"));
    btnClose = new QPushButton(tr("退出"));
    btnOpenDir = new QPushButton(tr("选择目录"));

    QHBoxLayout *HLay3 = new QHBoxLayout;
    HLay3->addStretch();
    HLay3->addWidget(btnOK);
    HLay3->addWidget(btnCancel);
    HLay3->addStretch();
    HLay3->addWidget(btnClose);
    HLay3->addWidget(btnOpenDir);

    //创建文本框,并设置初始字体
    txtEdit = new QPlainTextEdit;
    txtEdit->setPlainText("Hello world\n\nIt is my demo");

    QFont font = txtEdit->font(); //获取字体
    font.setPointSize(20);//修改字体大小
    txtEdit->setFont(font);//设置字体

    currentDir = new QString(QDir::currentPath());

    fileSystemModel = new QFileSystemModel;
    fileSystemModel->setRootPath(*currentDir);

    fileListTree = new QTreeView;
    fileListTree->setModel(fileSystemModel);
    fileListTree->setRootIndex(fileSystemModel->index(*currentDir));

    // imageLable
    imageLabel = new QLabel;
    imageLabel->hide();

    //创建垂直布局，并设置为主布局
    mainLayout = new QVBoxLayout;
    mainLayout->addLayout(HLay1); //添加字体类型组
    mainLayout->addLayout(HLay2);//添加字体颜色组
    mainLayout->addWidget(txtEdit);//添加PlainTextEdit
    mainLayout->addLayout(HLay3);//添加按键组
    mainLayout->addWidget(fileListTree);
    mainLayout->addWidget(imageLabel);

    setLayout(mainLayout); //设置为窗体的主布局
}

void MainWindow::iniSignalSlots()
{
    //三个颜色 QRadioButton 的clicked()信号与setTextFontColor()槽函数关联
    connect(rBtnBlue,SIGNAL(clicked()),this,SLOT(setTextFontColor()));
    connect(rBtnRed,SIGNAL(clicked()),this,SLOT(setTextFontColor()));
    connect(rBtnBlack,SIGNAL(clicked()),this,SLOT(setTextFontColor()));
    //三个字体设置的 QCheckBox 的clicked(bool)信号与相应的槽函数关联
    connect(chkBoxUnder,SIGNAL(clicked(bool)),
    this,SLOT(on_chkBoxUnder(bool)));
    connect(chkBoxItalic,SIGNAL(clicked(bool)),
    this,SLOT(on_chkBoxItalic(bool)));
    connect(chkBoxBold,SIGNAL(clicked(bool)),
    this,SLOT(on_chkBoxBold(bool)));
    //三个按钮的信号与窗体的槽函数关联
    connect(btnOK,SIGNAL(clicked()),this,SLOT(accept()));
    connect(btnCancel,SIGNAL(clicked()),this,SLOT(reject()));
    connect(btnClose,SIGNAL(clicked()),this,SLOT(close()));
    connect(btnOpenDir,SIGNAL(clicked()),this,SLOT(openDir()));
    connect(fileListTree,SIGNAL(doubleClicked(const QModelIndex)),this,SLOT(treeDoubleClicked(const QModelIndex)));

    // tab 使用见 https://doc.qt.io/qt-5/qtwidgets-dialogs-tabdialog-example.html
    // TODO: 1.组件排版调整，两种页面(使用 tab)，第一个页面是 TreeView；第二个页面是 imageLabel
    //       2.第一个页面功能：a.选择漫画收藏夹根路径，展示其下的所有漫画文件夹
    //                     b.双击文件夹，进入第二个页面
    //       3.第二个页面功能：a.左右翻页浏览图片； b.左翻到第一页，能进入上一个漫画的图片最后一页
    //                     c.同样最后一页再右翻，就是下一个漫画的图片第一页
    //                     d.显示 当前页/总页数，当前页从 1 开始
    //       4.记录看过的漫画，下次打开时，点击继续按钮，从上一次关闭时的地方看起。
}
