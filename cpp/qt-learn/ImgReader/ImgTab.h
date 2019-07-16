#ifndef IMGTAB_H
#define IMGTAB_H

#include <QWidget>
#include <QLabel>
#include <QScrollArea>

// 图片查看
class ImgTab : public QWidget
{
    Q_OBJECT
private:
    QLabel *imageLabel;
    QScrollArea *scrollArea;
public:
    explicit ImgTab(QWidget *parent = nullptr);
    void changeImg(QString fullFileName);
signals:

public slots:
};

#endif // IMGTAB_H
