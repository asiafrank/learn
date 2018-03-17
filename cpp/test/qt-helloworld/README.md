# qt-test
使用 cmake 导入 Boost 并构建 Qt 的 helloworld 程序

- 开发环境 Clion 2016.3.3
- cmake 3.8.0-rc1
- MinGW (gcc 5.3.0)
- Boost 1.63.0
- Qt: qt-opensource-windows-x86-mingw530-5.8.0

#### Boost 1.63.0
编译前,先下载 MinGW (gcc 5.3.0)<br/>
编译指令
```shell
bootstrap
b2 stage --toolset=gcc address-model=32 --without-python --stagedir="D:\boost_1_63_0\gcc32" link=static runtime-link=static threading=multi debug release
```
#### Clion 中 cmake 环境变量设置(根据自己安装环境做相应更改)
```shell
BOOST_LIBRARYDIR=D:\boost_1_63_0\gcc32\lib
CMAKE_PREFIX_PATH=D:\Qt\Qt_5_8_0\5.8\mingw53_32\lib\cmake
```