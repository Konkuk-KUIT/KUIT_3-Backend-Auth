package kuit3.backend.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import kuit3.backend.common.interceptor.JwtAuthInterceptor;
import kuit3.backend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class JwtAuthHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtAuthInterceptor jwtAuthInterceptor;
    private final AuthService authService;

    public JwtAuthHandlerArgumentResolver(JwtAuthInterceptor jwtAuthInterceptor, AuthService authService) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(JWTAuthorize.class);
        boolean hasType = long.class.isAssignableFrom(parameter.getParameterType());
        log.info("hasAnnotation={}, hasType={}, hasAnnotation && hasType={}", hasAnnotation, hasType, hasAnnotation&&hasType);
        return hasAnnotation && hasType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        log.info("userId={}", request.getAttribute("userId"));
        String accessToken = jwtAuthInterceptor.resolveAccessToken(request);
        jwtAuthInterceptor.validateAccessToken(accessToken);
        String email = jwtAuthInterceptor.getEmail(accessToken);
        jwtAuthInterceptor.validatePayload(email);
        long userId = authService.getUserIdByEmail(email);

        // HttpServletRequest에 userId 속성 설정
        //HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        request.setAttribute("userId", userId);
        return userId;
    }
}