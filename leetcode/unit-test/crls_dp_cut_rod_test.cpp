#include "stdafx.h"
#include "CppUnitTest.h"
#include <vector>
#include <string>
#include <sstream>
#include "../code/crls_dp_cut_rod.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace unittest
{
    using V = std::vector<std::uint32_t>;
    using PV = std::shared_ptr<V>;

    TEST_CLASS(CRLS_DP_CUT_ROD_TEST)
    {
    public:
        TEST_METHOD(MemorizedRecursiveCutRod)
        {
            /*
            i  | 1 | 2 | 3 | 4 |  5 |  6 |  7 |  8 |  9 | 10 |
            p  | 1 | 5 | 8 | 9 | 10 | 17 | 17 | 20 | 24 | 30 |
            */
            V p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
            V r(10 + 1, 0);
            PV prices = std::make_shared<V>(p);
            PV result = std::make_shared<V>(r);
            uint32_t r4 = clrs::MemorizedRecursiveCutRod(prices, 4, result);

            Assert::AreEqual(10, (int)r4);
        }

        TEST_METHOD(CutRod)
        {
            /*
            i  | 1 | 2 | 3 | 4 |  5 |  6 |  7 |  8 |  9 | 10 |
            p  | 1 | 5 | 8 | 9 | 10 | 17 | 17 | 20 | 24 | 30 |
            */
            V p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
            PV s = std::make_shared<V>(4 + 1, 0);
            uint32_t r4 = clrs::CutRod(p, 4, s);

            Assert::AreEqual(10, (int)r4);

            Logger::WriteMessage("Print Cut Rod Solution");

            uint32_t n = 4;
            std::ostringstream os;
            while (n > 0)
            {
                os << s->at(n) << " ";
                n = n - s->at(n);
            }
            Logger::WriteMessage(os.str().c_str());
        }
    };
}