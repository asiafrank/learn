# 使用CMake Tools插件（可选，如果这个项目去到一个没有这个插件的机器也同样可以生成项目）
include(CMakeToolsHelpers OPTIONAL)

# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.8)

# 项目名称
project(UseLevelDB)

set(INC_DIR /Users/asiafrank/workspace/leveldb/include)
# set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,-rpath -Wl,/usr/local/lib") #新加行，编译选项，添加库路径

find_library(levelDB NAMES leveldb HINTS "/Users/asiafrank/workspace/leveldb/build")
link_libraries(${levelDB})

include_directories(${INC_DIR})

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_ROOT_SRCS变量
aux_source_directory(. DIR_ROOT_SRCS)

# 添加 Lib子目录
add_subdirectory(lib)

# 指定生成目标
add_executable(UseLevelDB main.cpp ${DIR_ROOT_SRCS})

# 添加链接库
target_link_libraries(UseLevelDB PrinterLib)
target_link_libraries(UseLevelDB ${levelDB})
