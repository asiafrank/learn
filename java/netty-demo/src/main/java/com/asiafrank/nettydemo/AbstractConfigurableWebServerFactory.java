package com.asiafrank.nettydemo;

import org.springframework.boot.web.server.*;

import java.net.InetAddress;
import java.util.Set;

/**
 * @author zhangxiaofan 2020/11/28-15:56
 */
public class AbstractConfigurableWebServerFactory implements ConfigurableWebServerFactory {
    @Override
    public void setPort(int port) {

    }

    @Override
    public void setAddress(InetAddress address) {

    }

    @Override
    public void setErrorPages(Set<? extends ErrorPage> errorPages) {

    }

    @Override
    public void setSsl(Ssl ssl) {

    }

    @Override
    public void setSslStoreProvider(SslStoreProvider sslStoreProvider) {

    }

    @Override
    public void setHttp2(Http2 http2) {

    }

    @Override
    public void setCompression(Compression compression) {

    }

    @Override
    public void setServerHeader(String serverHeader) {

    }

    @Override
    public void addErrorPages(ErrorPage... errorPages) {

    }
}
