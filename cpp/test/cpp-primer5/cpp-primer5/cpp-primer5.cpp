// cpp-primer5.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include "Sales_item.h"
#include <cctype>
#include <vector>
#include "G.h"
#include <iterator>

void sales_items_m();

G g_invoke();

G g_invoke0();

int main()
{
    /*
    std::cout << "Hello, world" << std::endl;

    g_invoke();
    int x = 10;
    int *y = nullptr;
    y = &x;
    *y = *y * *y;
    std::cout << "x: " << x << " y:" << *y << std::endl;

    int &m = x;
    m = 20;
    std::cout << "x: " << x << " m:" << m << std::endl;

    int i = 42;
    int &r0 = i;
    const int &r1 = i;
    const int &r2 = 42;
    const int &r3 = r1 * 2;

    std::cout << "r0: " << r0 << " i: " << i << " r1: " << r1 << std::endl;
    r0 = 99;
    std::cout << "r0: " << r0 << " i: " << i << " r1: " << r1 << std::endl;

    static int qv = 20;
    constexpr int v = 10;
    const int *p = &v;
    const int *const pip = &v;
    constexpr int *q = &qv;

    using std::string;
    using std::cout;
    using std::endl;

    string s("some string");
    for (char &c : s)
    {
        cout << c << endl;
        c = toupper(c);
    }
    cout << s << endl;*/

    //sales_items_m();

    //--------- vector ---------
    using std::string;
    using std::vector;
    vector<string> articles{ "a", "an", "the" };
    for (string s : articles)
    {
        std::cout << s << " ";
    }
    std::cout << std::endl;

    for (vector<string>::const_iterator it = articles.begin(); it != articles.end(); ++it)
    {
        std::cout << *it << " ";
    }
    std::cout << std::endl;

    std::cout << "===================" << std::endl;

    vector<int> v1;
    for (size_t i = 0; i < 100; i++)
        v1.push_back(i);

    for (int v : v1)
        std::cout << v << " ";
    std::cout << std::endl;

    int arr[] = { 0,1,2,3,4,5,6,7,8,9 };
    int *p = arr;

    int *e = &arr[10];

    for (int *b = arr; b != e; b++)
    {
        std::cout << *b << " ";
    }
    std::cout << std::endl;

    int ia[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    int *beg = std::begin(ia);
    int *last = std::end(ia);
    for (; beg != last; beg++)
    {
        std::cout << *beg << " ";
    }
    std::cout << std::endl;

    int i = 0;
    std::cout << i << " " << ++i << std::endl;

    vector<int> v = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    vector<int> v0 = v;
    vector<int>::const_iterator v_cbegin = v.cbegin(), v_cend = v.cend();
    vector<int>::const_iterator v0_cbegin = v0.cbegin(), v0_cend = v0.cend();
    std::cout << (&v_cbegin == &v0_cbegin) << std::endl;
    while (v_cbegin != v_cend)
    {
        std::cout << &(*v_cbegin) << " ";
        ++v_cbegin;
    }
    std::cout << std::endl;

    while (v0_cbegin != v0_cend)
    {
        std::cout << &(*v0_cbegin) << " ";
        ++v0_cbegin;
    }
    std::cout << std::endl;

    vector<int>::const_reverse_iterator v_crbegin = v.crbegin(), v_crend = v.crend();
    while (v_crbegin != v_crend)
    {
        std::cout << &(*v_crbegin) << " ";
        ++v_crbegin;
    }
    std::cout << std::endl;

    vector<int> v2 = {5, 6};
    auto it2 = std::back_inserter(v2);
    it2 = 1;
    it2 = 2;

    vector<int>::const_iterator v2_cbegin = v2.cbegin(), v2_cend = v2.cend();
    while (v2_cbegin != v2_cend)
    {
        std::cout << *v2_cbegin << " ";
        ++v2_cbegin;
    }

    std::cout << std::endl;

    // lambda
    int k = 10;
    auto l = [k] () mutable {
        --k;
    };
    l();
    std::cout << k << std::endl;

	G g = g_invoke();

	std::cout << "then system pause" << std::endl;
    system("PAUSE");
    return 0;
}

void sales_items_m()
{
    std::cout << "------- Sales_item---------" << std::endl;

    Sales_item book;
    // read ISBN, number of copies sold, and sales price: 0-201-70353-X 4 24.99
    std::cin >> book;
    // write ISBN, number of copies sold, total revenue, and average price
    std::cout << book << std::endl;

    Sales_item item1, item2;
    // 0-201-78345-X 3 20.00
    // 0-201-78345-X 2 25.00
    std::cin >> item1 >> item2;

    if (item1.isbn() == item2.isbn())
    {
        std::cout << item1 + item2 << std::endl;
    }
    else
    {
        std::cerr << "Data must refer to same ISBN" << std::endl;
    }

    std::cout << "------- Sales_item end---------" << std::endl;
}

G g_invoke()
{
	std::cout << "then return g_invoke0" << std::endl;
	return g_invoke0();
}

G g_invoke0()
{
	std::cout << "G init" << std::endl;
	return G();
}

