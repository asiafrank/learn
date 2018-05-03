尝试做一个远程代理，能执行 python, c 代码并将结果返回

跨语言传输对象方案
- python object -> java object `buildingObject.__tojava__`
- c++ object    -> java object 使用 jna Structure
- groovy        -> groovy 执行库

操作命令:
```shell
telnet 127.0.0.1 8007
cpp sample
groovy sample
py sample
```