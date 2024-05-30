package kuit3.backend.config;

import kuit3.backend.common.argument_resolver.JwtAuthHandlerArgumentResolver;
import kuit3.backend.common.argument_resolver.JwtUserHandlerArgumentResolver;
import kuit3.backend.common.interceptor.JwtAuthInterceptor;
import kuit3.backend.common.interceptor.JwtUserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthInterceptor jwtAuthenticationInterceptor;
    private final JwtAuthHandlerArgumentResolver jwtAuthHandlerArgumentResolver;

    private final JwtUserInterceptor jwtUserInterceptor;
    private final JwtUserHandlerArgumentResolver jwtUserHandlerArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .order(1)
                .addPathPatterns("/auth/test");

        List<String> userUrls = new ArrayList<>();
        userUrls.add("/users/{userId}/dormant");
        registry.addInterceptor(jwtUserInterceptor)
                .order(1)
                .addPathPatterns(userUrls);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtAuthHandlerArgumentResolver);
        resolvers.add(jwtUserHandlerArgumentResolver);
    }
}