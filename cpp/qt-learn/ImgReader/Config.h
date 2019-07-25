#ifndef CONFIG_H
#define CONFIG_H

#include <QString>
#include <QRect>
#include <QJsonObject>

// 图像阅读器的配置
class Config
{
public:
    Config();
    ~Config();
public:
    void init();
    int getWidth() const;
    int getHeight() const;
    QRect getRect() const;
private:
    QString *configFilePath;
    QJsonObject *jsonObj;
    int width;
    int height;
private:
    void load();
};

#endif // CONFIG_H
