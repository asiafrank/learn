#include <iostream>

#include "lib/Sorts.h"
#include "lib/Printer.h"

// VSCode 的 CMakeTools 插件 DEBUG 按钮不起作用。直接用 F5 按钮启动 DEBUG 程序

void InsertSortTest();
void MergeSortTest();
void BubbleSortTest();
void QuickSortTest();
void HeapSortTest();
void PrinterTest();
void printArray(int a[], int len);

int main() {
    InsertSortTest();
    MergeSortTest();
    BubbleSortTest();
    QuickSortTest();
    HeapSortTest();
    // PrinterTest();
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