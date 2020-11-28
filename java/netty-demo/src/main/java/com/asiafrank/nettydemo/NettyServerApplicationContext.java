package com.asiafrank.nettydemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @author zhangxiaofan 2020/11/28-15:06
 */
public class NettyServerApplicationContext extends AnnotationConfigApplicationContext implements ConfigurableApplicationContext {

    private volatile WebServer nettyServer;

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        super.refresh();
        try {
            createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    private void createWebServer() {
        WebServer webServer = this.nettyServer;
        if (webServer == null) {
            NettyServerFactory factory = getWebServerFactory();
            this.nettyServer = factory.getWebServer();
        }
    }

    private NettyServerFactory getWebServerFactory() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory()
                .getBeanNamesForType(NettyServerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException(
                    "Unable to start NettyServerApplicationContext due to missing "
                            + "NettyServerFactory bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException(
                    "Unable to start NettyServerApplicationContext due to multiple "
                            + "NettyServerFactory beans : "
                            + StringUtils.arrayToCommaDelimitedString(beanNames));
        }
        return getBeanFactory().getBean(beanNames[0], NettyServerFactory.class);
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();
        WebServer webServer = startWebServer();
        if (webServer != null) {
            publishEvent(new NettyServerInitializedEvent(webServer, this));
        }
    }

    private WebServer startWebServer() {
        WebServer server = this.nettyServer;
        if (server != null) {
            server.start();
        }
        return server;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
    }
}
