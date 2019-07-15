#include "ImgTab.h"

#include <QVBoxLayout>
#include <QImageReader>

ImgTab::ImgTab(QWidget *parent) : QWidget(parent)
{
    imageLabel = new QLabel;
    imageLabel->setText("图片占位");

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addWidget(imageLabel);

    setLayout(mainLayout);
}

void ImgTab::changeImg(QString fullFileName)
{
    QImageReader reader(fullFileName);
    reader.setAutoTransform(true);
    const QImage image = reader.read();
    QPixmap p = QPixmap::fromImage(image);

    int w = imageLabel->width();
    int h = imageLabel->height();

    imageLabel->setPixmap(p.scaled(w, h, Qt::KeepAspectRatio));
}
