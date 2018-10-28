#pragma once

#include "stdafx.h"
#include <vector>
#include <memory>

namespace crls {
    using namespace std;

    /*
    CRLS-动态规划-钢条切割问题 P204
    给定一段长度为 n 英寸的钢条和一个价格表 p_i(i=1,2,...,n)，求切割方案。使销售收益 r_n 最大。
    注意，如果长度为 n 英寸的钢条的价格 p_n 足够大，最优解可能就是完全不需要切割。

    对于 r_n(n >= 2)，我们可以用更短的钢条的最优收割收益来描述它：
      r_n = max(p_n, r_1 + r_n-1, r_2 + r_n-2, ... , r_n-1 + r1)
    */

    /*
    自顶向下（递归）
    @param prices 价格表，索引从 1 开始，索引 0 的值永远是 0.
    @param n 需要切割钢条的长度
    @param r 最大收益表，记忆求解最大收益的过程
    */
    uint32_t API MemorizedRecursiveCutRod(shared_ptr<vector<uint32_t>> prices, uint32_t n, shared_ptr<vector<uint32_t>> r) {
        if (r->at(n) > 0)
            return r->at(n);

        if (n == 0) {
            r->at(0) = 0;
            return 0;
        }

        uint32_t i = 1; // 当前左侧切割的长度
        r->at(n) = 0;
        while (i <= n)  // max 方法逻辑
        {
            uint32_t currentR = prices->at(i) + MemorizedRecursiveCutRod(prices, n - i, r);
            if (currentR > r->at(n))
                r->at(n) = currentR;
            i++;
        }
        return r->at(n);
    };

    /*
    自底向上（迭代）
    @param prices 价格表，索引从 1 开始，索引 0 的值永远是 0.
    @param n 需要切割钢条的长度
    @param s 切割钢条长度的结果
    */
    uint32_t API CutRod(vector<uint32_t> prices, uint32_t n, shared_ptr<vector<uint32_t>> s) {
        if (n == 0)
            return 0;

        vector<uint32_t> r(n + 1, 0); // 初始化都是 0，当然 r[0] 就是 0
        uint32_t l = 0; // 左边切割的长度，初始化为 0
        while (l <= n)
        {
            uint32_t i = 1;    // 相对于 l 来说，当前左边切割的长度，替换递归方法中的 i
            while (i <= l)     // max 方法逻辑
            {
                uint32_t currentR = prices[i] + r[l - i];
                if (currentR > r[l]) {
                    r[l] = currentR;
                    s->at(l) = i; // 将当前最大收益，左边切割的长度记录到 s 中
                }
                i++;
            }
            l++;
        }

        return r[n];
    };
}

