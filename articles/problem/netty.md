## Netty 疑问汇总
### bossGroup vs workGroup
bossGroup 对应 Reactor 模型中的 acceptor 所运行的线程（一个 acceptor 只能绑定一个端口），`new NioEventLoopGroup` 时，传值 1 即可。

如果一个 Java 进程绑定 n 个端口，除了 new 出 n 个 NioEventLoopGroup(1) 作为 bossGroup 之外，还能 `new NioEventLoopGroup` 时，传值 n。然后绑定到各个 BootStrap 中。

workGroup 对应 worker, 数据处理的线程，根据需求做调整即可。

针对 bossGroup 的代码实例：
```java
// 1. 绑定单个端口
EventLoopGroup bossGroup = new NioEventLoopGroup(1);
EventLoopGroup workerGroup = new NioEventLoopGroup();
try {
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
    //... set options and handlers

    // Bind and start to accept incoming connections.
    ChannelFuture f = b.bind(port).sync();
    f.channel().closeFuture().sync();
} finally {
    workerGroup.shutdownGracefully();
    bossGroup.shutdownGracefully();
}
// ------------------------------
// 2. 绑定多个端口
CountDownLatch latch = new CountDownLatch(2);
ExecutorService exec = Executors.newCachedThreadPool();

EventLoopGroup bossGroup = new NioEventLoopGroup(2); // 两个 boss 线程
EventLoopGroup workerGroup = new NioEventLoopGroup();
try {
    ServerBootstrap b1 = new ServerBootstrap();
    b1.group(bossGroup, workerGroup)
    //... set options and handlers

    ServerBootstrap b2 = new ServerBootstrap();
    b2.group(bossGroup, workerGroup)
    //... set options and handlers

    exec.execute(()->{
        // Bind and start to accept incoming connections.
        ChannelFuture f = b1.bind(port1).sync();
        f.channel().closeFuture().sync();
        latch.countDown();
    });
    exec.execute(()->{
        // Bind and start to accept incoming connections.
        ChannelFuture f = b2.bind(port2).sync();
        f.channel().closeFuture().sync();
        latch.countDown();
    });
    latch.await();
} finally {
    workerGroup.shutdownGracefully();
    bossGroup.shutdownGracefully();
    exec.shutdown();
}
```


Netty 可靠性
https://my.oschina.net/tantexian/blog/716004

option vs childOption
https://stackoverflow.com/questions/35496345/what-is-the-difference-between-serverbootstrap-option-and-serverbootstrap-chil

https://www.jianshu.com/p/0bff7c020af2

Netty 文件上传
https://blog.csdn.net/a953713428/article/details/72792909
https://my.oschina.net/yybear/blog/201297

Netty 执行顺序
https://my.oschina.net/jamaly/blog/272385

Netty 的引用计数
https://my.oschina.net/rpgmakervx/blog/687190
http://ifeve.com/reference-counted-objects/
http://netty.io/wiki/reference-counted-objects.html

零拷贝技术
- https://www.ibm.com/developerworks/cn/linux/l-cn-zerocopy1/
- https://www.ibm.com/developerworks/cn/linux/l-cn-zerocopy2/