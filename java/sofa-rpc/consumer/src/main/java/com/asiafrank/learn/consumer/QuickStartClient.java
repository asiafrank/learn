package com.asiafrank.learn.consumer;

import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.asiafrank.learn.core.HelloService;

/**
 * Quick Start client
 */
public class QuickStartClient {
    public static void main(String[] args) {
        ApplicationConfig app = new ApplicationConfig();
        app.setAppName("DemoConsumer");
        // 指定注册中心
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("zookeeper")
                .setAddress("10.1.53.20:2181");
        // 引用一个服务
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setProtocol("bolt")
                .setTimeout(1000)
                .setApplication(app)
                .setRegistry(registryConfig);
        // 拿到代理类
        HelloService service = consumerConfig.refer();

        // 发起调用
        while (true) {
            System.out.println(service.sayHello("world"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}