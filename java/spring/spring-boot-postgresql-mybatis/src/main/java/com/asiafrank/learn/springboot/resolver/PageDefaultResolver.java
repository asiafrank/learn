package com.asiafrank.learn.springboot.resolver;

import com.asiafrank.learn.springboot.core.base.Pageable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class PageDefaultResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
            throws Exception
    {
        String pn = webRequest.getParameter("pageNum");
        String ps = webRequest.getParameter("pageSize");

        Pageable pageable = null;
        if (isNotEmpty(pn) && isNotEmpty(ps)) {
            pageable = Pageable.of(Integer.valueOf(pn), Integer.valueOf(ps));
        } else if (parameter.hasParameterAnnotation(PageDefault.class)) {
            PageDefault pd = parameter.getParameterAnnotation(PageDefault.class);
            if (Objects.nonNull(pd))
                pageable = Pageable.of(pd.pageNum(), pd.pageSize());
        }
        // else ignore
        return pageable;
    }

    private boolean isNotEmpty(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}
