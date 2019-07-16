#include "ImgTab.h"

#include <QVBoxLayout>
#include <QImageReader>

#include <QDebug>

ImgTab::ImgTab(QWidget *parent)
    : QWidget(parent)
    , imageLabel(new QLabel)
    , scrollArea(new QScrollArea)

{

    imageLabel->setText("图片占位");
    scrollArea->setBackgroundRole(QPalette::Dark);
    imageLabel->setSizePolicy(QSizePolicy::Ignored, QSizePolicy::Ignored);
    imageLabel->setScaledContents(true);

    scrollArea->setWidget(imageLabel);
    scrollArea->setBackgroundRole(QPalette::Dark);
    scrollArea->setVisible(true);

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addWidget(scrollArea);

    setLayout(mainLayout);
}

void ImgTab::changeImg(QString fullFileName)
{
    QImageReader reader(fullFileName);
    reader.setAutoTransform(true);
    const QImage image = reader.read();

    imageLabel->setPixmap(QPixmap::fromImage(image));
    imageLabel->resize(imageLabel->pixmap()->size());

    // TODO: fitToWindow 适应窗口
    // scrollArea->setWidgetResizable(true);
}
