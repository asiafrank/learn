#pragma once

#include "stdafx.h"
#include <vector>
#include <map>
#include <queue>

namespace Q {
    using namespace std;
    class Node
    {
    public:
        Node()
        {}
        Node *left;
        Node *right;
    };

    /*
    给定一棵树，求这棵树有多少种输入序列。
    input: [2,1,3]
    构建成树     2
               /  \
              1    3
    这棵树有两种输入序列: [2,1,3], [2,3,1]
    output: 2
    */
    int solution(vector<int> v)
    {

    }
} // Q end