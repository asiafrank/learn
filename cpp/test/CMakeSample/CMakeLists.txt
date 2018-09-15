# CMakeList.txt : CMake project for CMakeSample, include source and define
# project specific logic here.
#
cmake_minimum_required (VERSION 3.8)

include_directories(../../asio-learn/asio-include)

# Add source to this project's executable.
add_executable (CMakeSample "CMakeSample.cpp" "CMakeSample.h")
# TODO: Add tests and install targets if needed.
