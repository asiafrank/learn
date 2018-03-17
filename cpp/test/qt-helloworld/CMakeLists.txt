cmake_minimum_required(VERSION 3.8)
project(qt_test)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp)

set(Boost_USE_STATIC_LIBS ON)
set(Boost_USE_STATIC_RUNTIME ON)
set(Boost_USE_MULTITHREADED ON)
find_package(Boost 1.63.0 REQUIRED COMPONENTS
        regex
        system
        atomic
        date_time
        random)
include_directories(${Boost_INCLUDE_DIR})

find_package(Qt5Widgets REQUIRED)

add_executable(qt_test ${SOURCE_FILES})
target_link_libraries(${PROJECT_NAME} Qt5::Widgets
        Boost::regex
        Boost::system
        Boost::atomic
        Boost::date_time
        Boost::random)