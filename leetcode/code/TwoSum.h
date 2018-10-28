#pragma once

// two-sum.cpp : Defines the entry point for the console application.
// https://leetcode-cn.com/problems/two-sum/description/

/*
����һ�����������һ��Ŀ��ֵ���ҳ������к�ΪĿ��ֵ����������
����Լ���ÿ������ֻ��Ӧһ�ִ𰸣���ͬ����Ԫ�ز��ܱ��ظ����á�
ʾ��:
���� nums = [2, 7, 11, 15], target = 9
��Ϊ nums[0] + nums[1] = 2 + 7 = 9
���Է��� [0, 1]
*/

#include "stdafx.h"
#include <vector>
#include <map>

namespace TwoSum {
    using namespace std;

    class API Solution 
    {
    public:
        vector<int> twoSum(vector<int>& nums, int target) 
        {
            // Ĭ�ϴ�С����˳��
            // key: diff, value: index
            vector<int> result;
            map<int, int> diff_map;
            int len = nums.size();
            int x;
            int diff;
            for (int i = 0; i < len; i++)
            {
                x = nums[i];
                auto it = diff_map.find(x);
                if (it == diff_map.end())
                {
                    diff = target - x;
                    diff_map[diff] = i;
                }
                else
                {
                    return { it->second, i };
                }
            }
            return {};
        }
    };
}
