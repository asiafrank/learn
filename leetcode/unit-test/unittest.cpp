#include "stdafx.h"
#include "CppUnitTest.h"
#include <vector>
#include "../code/TwoSum.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace unittest
{
    TEST_CLASS(UnitTest1)
    {
    public:

        TEST_METHOD(TestMethod)
        {
            Assert::AreEqual(1, 1);
        }

        TEST_METHOD(TwoSum)
        {
            std::vector<int> nums = {2, 7, 11, 15};

            TwoSum::Solution s;
            std::vector<int> result = s.twoSum(nums, 9);
            Assert::AreEqual(0, result[0]);
            Assert::AreEqual(1, result[1]);
        }
    };
}