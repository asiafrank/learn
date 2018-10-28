#pragma once

#include "stdafx.h"
#include <vector>
#include <memory>

namespace crls {
    using namespace std;

    /*
    CRLS-��̬�滮-�����и����� P204
    ����һ�γ���Ϊ n Ӣ��ĸ�����һ���۸�� p_i(i=1,2,...,n)�����и����ʹ�������� r_n ���
    ע�⣬�������Ϊ n Ӣ��ĸ����ļ۸� p_n �㹻�����Ž���ܾ�����ȫ����Ҫ�и

    ���� r_n(n >= 2)�����ǿ����ø��̵ĸ����������ո���������������
      r_n = max(p_n, r_1 + r_n-1, r_2 + r_n-2, ... , r_n-1 + r1)
    */

    /*
    �Զ����£��ݹ飩
    @param prices �۸�������� 1 ��ʼ������ 0 ��ֵ��Զ�� 0.
    @param n ��Ҫ�и�����ĳ���
    @param r ����������������������Ĺ���
    */
    uint32_t API MemorizedRecursiveCutRod(shared_ptr<vector<uint32_t>> prices, uint32_t n, shared_ptr<vector<uint32_t>> r) {
        if (r->at(n) > 0)
            return r->at(n);

        if (n == 0) {
            r->at(0) = 0;
            return 0;
        }

        uint32_t i = 1; // ��ǰ����и�ĳ���
        r->at(n) = 0;
        while (i <= n)  // max �����߼�
        {
            uint32_t currentR = prices->at(i) + MemorizedRecursiveCutRod(prices, n - i, r);
            if (currentR > r->at(n))
                r->at(n) = currentR;
            i++;
        }
        return r->at(n);
    };

    /*
    �Ե����ϣ�������
    @param prices �۸�������� 1 ��ʼ������ 0 ��ֵ��Զ�� 0.
    @param n ��Ҫ�и�����ĳ���
    @param s �и�������ȵĽ��
    */
    uint32_t API CutRod(vector<uint32_t> prices, uint32_t n, shared_ptr<vector<uint32_t>> s) {
        if (n == 0)
            return 0;

        vector<uint32_t> r(n + 1, 0); // ��ʼ������ 0����Ȼ r[0] ���� 0
        uint32_t l = 0; // ����и�ĳ��ȣ���ʼ��Ϊ 0
        while (l <= n)
        {
            uint32_t i = 1;    // ����� l ��˵����ǰ����и�ĳ��ȣ��滻�ݹ鷽���е� i
            while (i <= l)     // max �����߼�
            {
                uint32_t currentR = prices[i] + r[l - i];
                if (currentR > r[l]) {
                    r[l] = currentR;
                    s->at(l) = i; // ����ǰ������棬����и�ĳ��ȼ�¼�� s ��
                }
                i++;
            }
            l++;
        }

        return r[n];
    };
}

