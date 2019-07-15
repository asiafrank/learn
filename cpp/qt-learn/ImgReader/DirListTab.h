#ifndef DIRLISTTAB_H
#define DIRLISTTAB_H

#include <QWidget>
#include <QPushButton>
#include <QFileSystemModel>
#include <QTreeView>

// 文件夹浏览表
class DirListTab : public QWidget
{
    Q_OBJECT
private:
    QPushButton      *btnOpenDir;
    QFileSystemModel *fileSystemModel;
    QString          *currentDir;
    QTreeView        *fileListTree;

    QTabWidget       *parent;
private slots:
    void openDir(); // 选择并打开一个文件夹
    void treeDoubleClicked(const QModelIndex &index); // 文件夹列表-双击事件

public:
    explicit DirListTab(QTabWidget *parent = nullptr);

signals:

public slots:
};

#endif // DIRLISTTAB_H
