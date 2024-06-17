package kuit3.backend.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import kuit3.backend.jwt.JwtUtil;
import kuit3.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JwtAuthHandlerArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtUtil jwtTokenUtil;
    private final AuthService authService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(JwtAuthrize.class);
        boolean hasType = long.class.isAssignableFrom(parameter.getParameterType());
        log.info("hasAnnotation={}, hasType={}, hasAnnotation && hasType={}", hasAnnotation, hasType, hasAnnotation&&hasType);
        return hasAnnotation && hasType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        log.info("userId={}", request.getAttribute("userId"));

        String accessToken = jwtTokenUtil.resolveAccessToken(request);
        jwtTokenUtil.validateAccessToken(accessToken);
        String email = jwtTokenUtil.getEmail(accessToken);
        jwtTokenUtil.validatePayload(email);
        long userId = authService.getUserIdByEmail(email);

        // HttpServletRequest에 userId 속성 설정
        //HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        request.setAttribute("userId", userId);
        return userId;
    }
}