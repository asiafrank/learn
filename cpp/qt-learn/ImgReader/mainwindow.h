#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QDialog>
#include <QCheckBox>
#include <QRadioButton>
#include <QPlainTextEdit>
#include <QPushButton>
#include <QFileSystemModel>
#include <QTreeView>

class MainWindow : public QWidget
{
    Q_OBJECT
private:
    QCheckBox       *chkBoxUnder;
    QCheckBox       *chkBoxItalic;
    QCheckBox       *chkBoxBold;
    QRadioButton    *rBtnBlack;
    QRadioButton    *rBtnRed;
    QRadioButton    *rBtnBlue;
    QPlainTextEdit  *txtEdit;
    QPushButton     *btnOK;
    QPushButton     *btnCancel;
    QPushButton     *btnClose;
    QPushButton     *btnOpenDir;

    QFileSystemModel *fileSystemModel;
    QString          *basePath;
    QTreeView        *fileListTree;

    void    iniUI();//UI 创建与初始化
    void    iniSignalSlots();//初始化信号与槽的链接
private slots:
    void on_chkBoxUnder(bool checked); //Underline 的clicked(bool)信号的槽函数
    void on_chkBoxItalic(bool checked);//Italic 的clicked(bool)信号的槽函数
    void on_chkBoxBold(bool checked); //Bold 的clicked(bool)信号的槽函数
    void setTextFontColor(); //设置字体颜色
    void openDir(); // 选择并打开一个文件夹
    void treeDoubleClicked(const QModelIndex &index); // 文件夹列表-双击事件

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
};

#endif // MAINWINDOW_H
