package com.asiafrank.learn.provider;

import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.asiafrank.learn.core.HelloService;

/**
 * Quick Start Server
 */
public class QuickStartServer {

    public static void main(String[] args) {
        ApplicationConfig app = new ApplicationConfig();
        app.setAppName("DemoProvider");
        // 指定注册中心
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("zookeeper")
                .setAddress("10.1.53.20:2181");
        // 指定服务端协议和地址
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt")
                .setPort(1234)
                .setDaemon(false);
        // 发布一个服务
        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl())
                .setApplication(app)
                .setRegistry(registryConfig)
                .setServer(serverConfig);
        providerConfig.export();
    }
}