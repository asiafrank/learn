# 使用CMake Tools插件（可选，如果这个项目去到一个没有这个插件的机器也同样可以生成项目）
include(CMakeToolsHelpers OPTIONAL)
# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.12.2)

# 项目名称
project(Vulkan)
set(CMAKE_REQUIRED_FLAGS -std=c++17)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(VulkanHome /Users/asiafrank/programs/vulkansdk-macos-1.1.106.0/macOS)

include_directories(/usr/local/include ${VulkanHome}/include)

find_library(VulkanLib NAMES vulkan VkLayer_core_validation HINTS "${VulkanHome}/lib")
find_library(GlfwLib NAMES glfw HINTS "/usr/local/lib")

link_libraries(${VulkanLib} ${GlfwLib})

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_ROOT_SRCS变量
aux_source_directory(. DIR_ROOT_SRCS)

# 指定生成目标
add_executable(Vulkan main.cpp ${DIR_ROOT_SRCS})

# 添加链接库
target_link_libraries(Vulkan ${VulkanLib} ${GlfwLib})

# Mac 上 CMake DEBUG 点击 Visual Studio Code 的 Debug 按钮才有效。