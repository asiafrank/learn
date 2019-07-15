#include "mainwindow.h"
#include <QApplication>

#include <QScreen>
#include <QStyle>
#include <QDir>
#include <QFile>
#include <QJsonObject>
#include <QJsonDocument>
#include <QDebug>

// TODO: 1.组件排版调整，两种页面(使用 tab)，第一个页面是 TreeView；第二个页面是 imageLabel
//       2.第一个页面功能：a.选择漫画收藏夹根路径，展示其下的所有漫画文件夹
//                     b.双击文件夹，进入第二个页面
//       3.第二个页面功能：a.左右翻页浏览图片； b.左翻到第一页，能进入上一个漫画的图片最后一页
//                     c.同样最后一页再右翻，就是下一个漫画的图片第一页
//                     d.显示 当前页/总页数，当前页从 1 开始
//       4.记录看过的漫画，下次打开时，点击继续按钮，从上一次关闭时的地方看起。
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QRect rec = QGuiApplication::primaryScreen()->geometry();
    int width = 0;
    int height = 0;

    // config file
    QString path = QDir::currentPath();
    QString configFileName = path + "/" + "config.json";

    QFile f(configFileName);
    if (f.exists()) {
        if (!f.open(QIODevice::ReadOnly)) {
            qWarning("Couldn't open save file.");
            return 1;
        }

        QByteArray jsonData = f.readAll();
        QJsonDocument jsonDoc = QJsonDocument::fromJson(jsonData);
        QJsonObject jsonObj = jsonDoc.object();
        qDebug() << "file exist: {" << jsonObj["width"] << "," << jsonObj["height"];
        width = jsonObj["width"].toInt();
        height = jsonObj["height"].toInt();
    } else {
        if (!f.open(QIODevice::WriteOnly))
            return -1;

        width = int(rec.width()  * 0.7);
        height = int(rec.height() * 0.7);

        QJsonObject jsonObj;
        jsonObj["width"] = width;
        jsonObj["height"] = height;
        QJsonDocument jsonDoc(jsonObj);
        f.write(jsonDoc.toJson());
    }
    // TOOD：配置文件逻辑放到 MainWindow 对象中

    QSize newSize(width, height);
    MainWindow *main = new MainWindow;
    main->setWindowTitle("图片查看器");
    main->setGeometry(QStyle::alignedRect(
                          Qt::LeftToRight,
                          Qt::AlignCenter,
                          newSize,
                          rec
                      ));
    main->show();
    return a.exec();
}
