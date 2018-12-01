#include "lib/Printer.h"

// VSCode 的 CMakeTools 插件 DEBUG 按钮不起作用。直接用 F5 按钮启动 DEBUG 程序
int main() {
    Printer* printer = new Printer();
    printer->print();
    delete printer;
    return 0;
}