#include <iostream>
#include <thread>

#include "lib/Sorts.h"
#include "lib/Printer.h"

// VSCode 的 CMakeTools 插件 DEBUG 按钮不起作用。直接用 F5 按钮启动 DEBUG 程序

void hello() {
    std::cout << "Hello Concurrent World\n";
}

int main() {
    std::thread t(hello);
    t.join();
    return 0;
}