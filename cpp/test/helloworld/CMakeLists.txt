cmake_minimum_required(VERSION 3.6)
project(learn_cpp)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        src/HelloWorld.cpp)

add_executable(learn_cpp ${SOURCE_FILES})