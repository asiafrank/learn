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
    imageLabel->setPixmap(QPixmap::fromImage(image));
}
