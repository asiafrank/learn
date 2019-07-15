#include "DirListTab.h"

#include <QFileDialog>
#include <QImageReader>
#include <QVBoxLayout>
#include <QDebug>

#include "ImgTab.h"

void DirListTab::openDir()
{
    QString dlgTitle="选择一个文件"; //对话框标题
    QString dirName = QFileDialog::getExistingDirectory(this, dlgTitle, *currentDir);
    if (!dirName.isEmpty()) {
        currentDir->clear();
        currentDir->append(dirName);
        fileListTree->setRootIndex(fileSystemModel->index(dirName));
    }
}

void DirListTab::treeDoubleClicked(const QModelIndex &index)
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

            int currentIndex = parent->currentIndex();
            qDebug() << "currentIndex: " << currentIndex << "\n";

            ImgTab *imgTab = static_cast<ImgTab*>(parent->widget(1));
            imgTab->changeImg(fullFileName);
            parent->setCurrentIndex(1); // 切换到 ImgTab
            return;
        }
    }
}

DirListTab::DirListTab(QTabWidget *parent) : QWidget(parent)
{
    this->parent = parent;
    // btnOpenDir 对应的 content fileListTree 设置
    currentDir = new QString(QDir::currentPath());
    fileSystemModel = new QFileSystemModel;
    fileSystemModel->setRootPath(*currentDir);

    fileListTree = new QTreeView;
    fileListTree->setModel(fileSystemModel);
    fileListTree->setRootIndex(fileSystemModel->index(*currentDir));
    btnOpenDir = new QPushButton(tr("选择目录"));

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addWidget(btnOpenDir);
    mainLayout->addWidget(fileListTree);

    setLayout(mainLayout);

    connect(btnOpenDir,SIGNAL(clicked()),this,SLOT(openDir()));
    connect(fileListTree,SIGNAL(doubleClicked(const QModelIndex)),this,SLOT(treeDoubleClicked(const QModelIndex)));
}
