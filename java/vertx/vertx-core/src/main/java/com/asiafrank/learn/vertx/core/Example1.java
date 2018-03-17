package com.asiafrank.learn.vertx.core;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;

/**
 * http://vertx.io/docs/vertx-core/java/
 * @author zhangxf created at 9/30/2017.
 */
public class Example1 {
    public static void main(String[] args) {
        VertxOptions vOpt = new VertxOptions().setWorkerPoolSize(40);
        Vertx vertx = Vertx.vertx(vOpt);

        dont_call_us_we_ll_call_you(vertx);
//        running_blocking_code(vertx);
//        async_coordination(vertx);
//        sequential_composition(vertx);
    }

    private static void dont_call_us_we_ll_call_you(Vertx vertx) {
        vertx.setPeriodic(1000, id -> {
            System.out.printf("%d timer fired!\n", id);
        });
    }

    private static void running_blocking_code(Vertx vertx) {
        vertx.executeBlocking(f -> {
            String result = "hello";
            sleep(1000);
            f.complete(result);
        }, res -> {
            System.out.println("blocking complete " + res.result());
        });

        /*
        WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
        executor.executeBlocking(future -> {
            // Call some blocking API that takes a significant amount of time to return
            String result = someAPI.blockingMethod("hello");
            future.complete(result);
        }, res -> {
            System.out.println("The result is: " + res.result());
        });

        executor.close();
        */
    }

    private static void async_coordination(Vertx vertx) {
        Future<HttpServer> httpServerFuture = Future.future();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(req -> {
            req.response().end("Http Server Handle");
        });
        httpServer.listen(httpServerFuture.completer());

        Future<NetServer> netServerFuture = Future.future();
        NetServer netServer = vertx.createNetServer();
        netServer.connectHandler(socket -> {
            socket.write("Net server handle");
            socket.close();
        });
        netServer.listen(netServerFuture.completer());

        CompositeFuture.any(httpServerFuture, netServerFuture).setHandler(ar -> {
            if (ar.succeeded()) {
                System.out.println("At least one is succeeded");
            } else {
                System.out.println("All failed");
            }
        });
    }

    private static void sequential_composition(Vertx vertx) {
        FileSystem fs = vertx.fileSystem();
        Future<Void> startFuture = Future.future();
        startFuture.setHandler(ar -> {
            // 因为 compose 会自己 set，这里的 handler 会无效
            if (ar.succeeded())
                System.out.println("move file success");
            else
                System.out.println("move file failed");
        });

        Future<Void> done = Future.future();
        done.setHandler(ar -> {
            if (ar.succeeded())
                System.out.println("delete success");
            else
                System.out.println("delete failed");
        });

        Future<Void> fut1 = Future.future();
        fut1.setHandler(ar -> {
            // 因为 compose 会自己 set，这里的 handler 会无效
            if (ar.succeeded())
                System.out.println("create file success");
            else
                System.out.println("create file failed");
        });

        Future<Void> fut2 = Future.future();
        fut2.setHandler(ar -> {
            // 因为 compose 会自己 setHandler，这里的 handler 会无效
            if (ar.succeeded())
                System.out.println("write file success");
            else
                System.out.println("write file failed");
        });

        fs.createFile("foo", fut1.completer());
        fut1.compose(v -> {
            // When the file is created (fut1), execute this:
           fs.writeFile("foo", Buffer.buffer(), ar -> {
               // 为了能确认每步操作的成功，应该直接在这些方法上新写一个 handler，
               // 而不是用 future.completer();
               if (ar.succeeded())
                   System.out.println("write file foo success");
               else
                   System.out.println("write file foo failed");
           });
        }, fut2).compose(v -> {
            // When the file is written (fut2), execute this:
            fs.move("foo", "bar", startFuture.completer());
            // mark startFuture it as failed if any step fails.
        }, startFuture).compose(v -> {
            fs.delete("bar", done.completer());
        }, done);
        // 注: compose 方法会自己 setHandler，会将原来 set 的 handler 替换掉
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
