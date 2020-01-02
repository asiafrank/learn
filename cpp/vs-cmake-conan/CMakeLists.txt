# 使用CMake Tools插件（可选，如果这个项目去到一个没有这个插件的机器也同样可以生成项目）
include(CMakeToolsHelpers OPTIONAL)

# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.8)

# 项目名称
project(ConanExample)

# conan 设置
add_definitions("-std=c++11")

include(${CMAKE_BINARY_DIR}/conanbuildinfo.cmake)
conan_basic_setup()

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_ROOT_SRCS变量
aux_source_directory(. DIR_ROOT_SRCS)

# 添加 Lib子目录
add_subdirectory(lib)

# 指定生成目标
add_executable(ConanExample main.cpp ${DIR_ROOT_SRCS})

# 添加链接库
target_link_libraries(ConanExample PrinterLib ${CONAN_LIBS})

################################
# GTest
################################
add_subdirectory(googletest)
enable_testing()
include_directories(${gtest_SOURCE_DIR}/include ${gtest_SOURCE_DIR})

################################
# Unit Tests
################################
# Add test cpp file
add_executable( runUnitTests test/test.cpp )
# Link test executable against gtest & gtest_main
target_link_libraries(runUnitTests gtest gtest_main)
add_test( runUnitTests runUnitTests )