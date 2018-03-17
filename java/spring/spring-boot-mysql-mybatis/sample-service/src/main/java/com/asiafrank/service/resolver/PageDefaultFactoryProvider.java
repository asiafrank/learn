package com.asiafrank.service.resolver;

import com.asiafrank.core.base.Pageable;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import java.util.Objects;

/**
 * @author user created at 4/27/2017.
 */
@Singleton
public final class PageDefaultFactoryProvider extends AbstractValueFactoryProvider {

    @Singleton
    public static final class PageDefaultResolver extends ParamInjectionResolver<PageDefault> {
        public PageDefaultResolver() {
            super(PageDefaultFactoryProvider.class);
        }
    }

    private static final class PageDefaultValueFactory extends AbstractContainerRequestValueFactory<Pageable> {

        @Context
        private ResourceContext context;

        private final Pageable defaultPageable;

        PageDefaultValueFactory(Pageable defaultPageable) {
            this.defaultPageable = defaultPageable;
        }

        @Override
        public Pageable provide() {
            final HttpServletRequest request = context.getResource(HttpServletRequest.class);

            String pn = request.getParameter("pageNum");
            String ps = request.getParameter("pageSize");

            int pageNum, pageSize;

            if (Objects.isNull(pn) || pn.isEmpty()) pageNum = defaultPageable.getPageNum();
            else pageNum = Integer.valueOf(pn);

            if (Objects.isNull(ps) || ps.isEmpty()) pageSize = defaultPageable.getPageSize();
            else pageSize = Integer.valueOf(ps);

            return Pageable.of(pageNum, pageSize);
        }
    }

    @Inject
    protected PageDefaultFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
        super(mpep, locator, Parameter.Source.UNKNOWN);
    }

    @Override
    protected AbstractContainerRequestValueFactory<?> createValueFactory(Parameter parameter) {
        Class<?> classType = parameter.getRawType();

        if (Objects.isNull(classType) || (!classType.equals(Pageable.class))) {
            return null;
        }

        PageDefault pageDefault = parameter.getAnnotation(PageDefault.class);
        if (Objects.nonNull(pageDefault) && classType.isAssignableFrom(Pageable.class)) {
            return new PageDefaultValueFactory(Pageable.of(pageDefault.pageNum(), pageDefault.pageSize()));
        }

        return null;
    }
}
