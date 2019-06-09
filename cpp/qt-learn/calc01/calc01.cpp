#include "calc01.h"
#include "ui_calc01.h"

Calc01::Calc01(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Calc01)
{
    ui->setupUi(this);
}

Calc01::~Calc01()
{
    delete ui;
}

void Calc01::on_done_clicked()
{
    QString xStr = ui->x->text();
    QString yStr = ui->y->text();

    // 房租数额小，用 int 即可
    //
    double x = xStr.toDouble();
    double y = yStr.toDouble();

    double sum = x + y;
    QString s = QString::number(sum);
    ui->sumLabel->setText(s);
}
