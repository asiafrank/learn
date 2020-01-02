#include <gtest/gtest.h>

TEST(ExampleTest, Static) {
  EXPECT_EQ(std::string("Hello World!"), std::string("Hello World!"));
}