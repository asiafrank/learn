#include "Config.h"

#include <QSignalMapper>
#include <QGuiApplication>
#include <QScreen>
#include <QStyle>
#include <QDir>
#include <QFile>

#include <QJsonDocument>
#include <QMessageBox>
#include <QDebug>

Config::Config()
    :width(0)
    ,height(0)
{
    qDebug() << "config constructed";
}

Config::~Config()
{
    // TODO: 获取当前 窗口大小及 json配置，写入 config file 中
    qDebug() << "config descruted";
}

void Config::init()
{
    bool hasError = false;
    QMessageBox msg;

    QRect rec = QGuiApplication::primaryScreen()->geometry();

    // config file
    QString path = QDir::currentPath();
    configFilePath = new QString(path + "/" + "config.json");

    QFile f(*configFilePath);
    if (f.exists()) {
        if (!f.open(QIODevice::ReadOnly)) {
            msg.setText("Couldn't open save file.");
            hasError = true;
            return;
        }

        QByteArray jsonData = f.readAll();
        QJsonDocument jsonDoc = QJsonDocument::fromJson(jsonData);
        jsonObj = new QJsonObject(jsonDoc.object());
        width = jsonObj->take("width").toInt();
        height = jsonObj->take("height").toInt();
    } else {
        if (!f.open(QIODevice::WriteOnly)) {
            msg.setText("Couldn't open save file(WriteOnly).");
            hasError = true;
            return;
        }

        width = int(rec.width()  * 0.7);
        height = int(rec.height() * 0.7);

        QJsonObject jsonObj;
        jsonObj["width"] = width;
        jsonObj["height"] = height;
        QJsonDocument jsonDoc(jsonObj);
        f.write(jsonDoc.toJson());
    }

    if (hasError)
        msg.exec();
}


int Config::getWidth() const
{
    return width;
}

int Config::getHeight() const
{
    return height;
}

QRect Config::getRect() const
{
    return QGuiApplication::primaryScreen()->geometry();
}

// TODO: 将 jsonObject 里的数据赋值给 Config 的属性
void Config::load()
{

}
