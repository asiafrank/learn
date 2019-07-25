#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QDialog>
#include <QCheckBox>
#include <QRadioButton>
#include <QPlainTextEdit>
#include <QVBoxLayout>
#include <QLabel>

#include "Config.h"

class MainWindow : public QWidget
{
    Q_OBJECT
private:
    QTabWidget *tabWidget;

    QPushButton *listBtn;
    QPushButton *imageBtn;

    Config *config;

private:
    void    iniUI();//UI 创建与初始化
    void    iniSignalSlots();//初始化信号与槽的链接
private slots:
    void    setCurrentIndex(int index);

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
public slots:
    void exit();
};

#endif // MAINWINDOW_H
