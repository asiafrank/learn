#include "MainWindow.h"
#include <QApplication>

#include <QObject>



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
    MainWindow *main = new MainWindow;
    QObject::connect(&a, SIGNAL(aboutToQuit()), main, SLOT(exit()));
    main->show();
    return a.exec();
}
