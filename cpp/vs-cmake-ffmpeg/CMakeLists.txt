# 使用CMake Tools插件（可选，如果这个项目去到一个没有这个插件的机器也同样可以生成项目）
include(CMakeToolsHelpers OPTIONAL)

# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.8)

# 项目名称
project(cmake_ffmpeg_test)

set(CMAKE_CXX_STANDARD 11)
set(ENV{DYLD_LIBRARY_PATH} /Users/asiafrank/programs/ffmpeg-shared/bin)
set(INC_DIR /Users/asiafrank/programs/ffmpeg-dev/include)

# ～/.bash_profile 里配置 export DYLD_LIBRARY_PATH=/Users/asiafrank/programs/ffmpeg-shared/bin 才能运行
link_directories(/Users/asiafrank/programs/ffmpeg-shared/bin)
link_libraries(libavcodec.58.dylib 
libavdevice.58.dylib 
libavfilter.7.dylib 
libavformat.58.dylib
libavutil.56.dylib
libpostproc.55.dylib
libswresample.3.dylib
libswscale.5.dylib)

include_directories(${INC_DIR})

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_ROOT_SRCS变量
aux_source_directory(. DIR_ROOT_SRCS)

# 添加 Lib子目录
add_subdirectory(lib)

# 指定生成目标
add_executable(cmake_ffmpeg_test main.cpp ${DIR_ROOT_SRCS})

# 添加链接库
target_link_libraries(cmake_ffmpeg_test PrinterLib)