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
    ����һ��������������ж������������С�
    input: [2,1,3]
    ��������     2
               /  \
              1    3
    �������������������: [2,1,3], [2,3,1]
    output: 2
    */
    int solution(vector<int> v)
    {

    }
} // Q end