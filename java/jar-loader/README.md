# 在 JVM 运行时动态加载 jar
要求：
- 第三方 jar，在编译时依赖，依赖的类使用 new 关键字初始化
- 运行时 jar，使程序正确运行

查阅资料，一般都有以下两种做法：
- 1.建两个 maven module，一个是 api 模块只写接口，另一个是 impl 模块写 api 的实现，
将 api 接口定义的 jar 放入 classpath 中随 JVM 启动；然后使用自定义加载器，加载 impl.jar，使用工厂类来新建 api 接口的实例，
新建过程均使用自定义加载器 loadClass 然后调用 Class.newInstance 获取实例，赋值给 api 接口即可。
- 2.使用 javaagent，典型的是 [HotswapAgent](https://github.com/HotswapProjects/HotswapAgent) 项目，真正做到动态加载 jar，更新类定义。

TODO: 试试 java instrument 里的工具

#### FAQ
##### 1. loadClass 与 Class.forName 区别？
Class.forName(className) 即 Class.forName(className,true,classloader) 第二个参数为 true 表示 loadClass后必须初始化，
所以目标对象的 static 块代码会被执行，static 参数也被初始化。(方法执行结束，已经做了加载、连接（验证、准备、解析)、初始化这3个阶段了)

ClassLoader.loadClass(className) 即 ClassLoader.loadClass(className,false); 第二个参数为 false，该参数表示目标对象被装载后
不进行连接，这就意味这不会去执行该类静态块中间的内容。（方法执行结束，只做了加载步骤）

#### 参考
- 《深入理解Java虚拟机》 第7章(虚拟机类加载机制)及第9章(类加载及执行子系统的案例与实战)