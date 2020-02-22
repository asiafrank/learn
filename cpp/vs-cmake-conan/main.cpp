#include "Poco/MD5Engine.h"
#include "Poco/DigestStream.h"
#include "lib/Sorts.h"
#include "lib/Printer.h"

#include <iostream>

// VSCode 的 CMakeTools 插件 DEBUG 按钮不起作用。直接用 F5 按钮启动 DEBUG 程序
void InsertSortTest();
void MergeSortTest();
void BubbleSortTest();
void QuickSortTest();
void HeapSortTest();
void PrinterTest();
void printArray(int a[], int len);

// build 目录下
// 安装依赖：conan install ..
// 编译：cmake .. -G "Unix Makefiles" -DCMAKE_BUILD_TYPE=Release 或 -DCMAKE_BUILD_TYPE=Debug
//      cmake --build .
// 如果按 debug 编译，则 VSCode 中按 F5 debug 调试
int main() {
    InsertSortTest();
    MergeSortTest();
    BubbleSortTest();
    QuickSortTest();
    HeapSortTest();
    // PrinterTest();

    Poco::MD5Engine md5;
    Poco::DigestOutputStream ds(md5);
    ds << "abcdefghijklmnopqrstuvwxyz";
    ds.close();
    std::cout << Poco::DigestEngine::digestToHex(md5.digest()) << std::endl;

    double cash = 92174.25;
    double delta = 4320.0;
    double total = 0;
    for (int i = 0; i < 12; ++i) {
        total += cash;
        cash -= delta;
    }
    double t = total / 12;
    double rs = t * 15;
    std::cout << "total: " << total << std::endl;
    std::cout << "total / 12: " << t << std::endl;
    std::cout << "rs: " << rs << std::endl;
    return 0;
}

void InsertSortTest()
{
    int a[] = {7, 6, 5, 4, 3, 2, 1};
    int len = sizeof(a) / sizeof(int);

    clrs::InsertSort(a, len);
    printArray(a, len);
}

void MergeSortTest()
{
    int a[] = {7, 6, 5, 4, 3, 2, 1};
    int len = sizeof(a) / sizeof(int);
    clrs::MergeSort(a, 0, len - 1);
    printArray(a, len);
}

void BubbleSortTest()
{
    int a[] = {7, 6, 5, 4, 3, 2, 1};
    int len = sizeof(a) / sizeof(int);
    clrs::BubbleSort(a, len);
    printArray(a, len);
}

void QuickSortTest()
{
    int a[] = {7, 6, 5, 4, 3, 2, 1};
    int len = sizeof(a) / sizeof(int);
    clrs::QuickSort(a, 0, len - 1);
    printArray(a, len);
}

void HeapSortTest()
{
    // 堆排序，第一个元素为空
    int a[] = {0, 7, 6, 5, 4, 3, 2, 1};
    int len = sizeof(a) / sizeof(int);
    clrs::HeapSort(a, len);
    printArray(a, len);
}

void PrinterTest()
{
    Printer* printer = new Printer();
    printer->print();
    delete printer;
}

void printArray(int a[], int len)
{
    std::cout << "[";
    for (int i = 0; i < len; i++)
    {
        std::cout << a[i];
        if (i < len - 1)
            std::cout << ",";
    }
    std::cout << "]" << std::endl;
}