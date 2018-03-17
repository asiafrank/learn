package com.asiafrank.service.config;

import com.asiafrank.service.controller.SampleController;
import com.asiafrank.service.resolver.PageDefault;
import com.asiafrank.service.resolver.PageDefaultFactoryProvider;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        // don't use package scan, it doesn't work when execute `java -jar`
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        register(SampleController.class);
        register(new PageBinder());
    }

    private static final class PageBinder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(PageDefaultFactoryProvider.class)
                    .to(ValueFactoryProvider.class)
                    .in(Singleton.class);
            bind(PageDefaultFactoryProvider.PageDefaultResolver.class)
                    .to(new TypeLiteral<InjectionResolver<PageDefault>>() {})
                    .in(Singleton.class);
        }
    }
}