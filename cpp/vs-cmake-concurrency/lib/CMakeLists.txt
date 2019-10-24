include(CMakeToolsHelpers OPTIONAL)

cmake_minimum_required(VERSION 2.8)

aux_source_directory(. DIR_LIB_SRCS)

# 生成链接库
add_library(PrinterLib ${DIR_LIB_SRCS})