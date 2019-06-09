#ifndef CALC01_H
#define CALC01_H

#include <QMainWindow>

namespace Ui {
class Calc01;
}

class Calc01 : public QMainWindow
{
    Q_OBJECT

public:
    explicit Calc01(QWidget *parent = nullptr);
    ~Calc01();

private slots:
    void on_done_clicked();

private:
    Ui::Calc01 *ui;
};

#endif // CALC01_H
