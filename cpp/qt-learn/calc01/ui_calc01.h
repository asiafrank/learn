/********************************************************************************
** Form generated from reading UI file 'calc01.ui'
**
** Created by: Qt User Interface Compiler version 5.12.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CALC01_H
#define UI_CALC01_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Calc01
{
public:
    QWidget *centralWidget;
    QLineEdit *x;
    QLineEdit *y;
    QPushButton *done;
    QLabel *sumLabel;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;

    void setupUi(QMainWindow *Calc01)
    {
        if (Calc01->objectName().isEmpty())
            Calc01->setObjectName(QString::fromUtf8("Calc01"));
        Calc01->resize(400, 300);
        centralWidget = new QWidget(Calc01);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        x = new QLineEdit(centralWidget);
        x->setObjectName(QString::fromUtf8("x"));
        x->setGeometry(QRect(20, 10, 113, 21));
        y = new QLineEdit(centralWidget);
        y->setObjectName(QString::fromUtf8("y"));
        y->setGeometry(QRect(20, 40, 113, 21));
        done = new QPushButton(centralWidget);
        done->setObjectName(QString::fromUtf8("done"));
        done->setGeometry(QRect(20, 70, 113, 32));
        sumLabel = new QLabel(centralWidget);
        sumLabel->setObjectName(QString::fromUtf8("sumLabel"));
        sumLabel->setGeometry(QRect(180, 40, 59, 16));
        Calc01->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(Calc01);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 400, 22));
        Calc01->setMenuBar(menuBar);
        mainToolBar = new QToolBar(Calc01);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        Calc01->addToolBar(Qt::TopToolBarArea, mainToolBar);

        retranslateUi(Calc01);

        QMetaObject::connectSlotsByName(Calc01);
    } // setupUi

    void retranslateUi(QMainWindow *Calc01)
    {
        Calc01->setWindowTitle(QApplication::translate("Calc01", "Calc01", nullptr));
        done->setText(QApplication::translate("Calc01", "Done", nullptr));
        sumLabel->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class Calc01: public Ui_Calc01 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CALC01_H
