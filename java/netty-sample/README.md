尝试做一个远程代理，能执行 python, c 代码并将结果返回

跨语言传输对象方案
- python object -> java object `buildingObject.__tojava__`
- c++ object    -> java object 通过 jna
- groovy        -> groovy 执行库

python, groovy 这种解释代码需要加密成二进制（头部加入脚本声明），解密后再执行。
