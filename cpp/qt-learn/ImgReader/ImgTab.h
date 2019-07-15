#ifndef IMGTAB_H
#define IMGTAB_H

#include <QWidget>
#include <QLabel>

// 图片查看
class ImgTab : public QWidget
{
    Q_OBJECT
private:
    QLabel *imageLabel;
public:
    explicit ImgTab(QWidget *parent = nullptr);
    void changeImg(QString fullFileName);
signals:

public slots:
};

#endif // IMGTAB_H
