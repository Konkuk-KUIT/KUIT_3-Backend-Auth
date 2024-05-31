package kuit3.backend.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import kuit3.backend.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit3.backend.jwt.JwtProvider;
import kuit3.backend.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info(parameter.getParameterName() + parameter.getParameterType());
        boolean hasAnnotation = parameter.hasParameterAnnotation(PreAuthorize.class);
        boolean hasType = Long.class.isAssignableFrom(parameter.getParameterType());
        log.info("hasAnnotation={}, hasType={}, hasAnnotation && hasType={}", hasAnnotation, hasType, hasAnnotation&&hasType);
        return true;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = (String)request.getAttribute("jwtToken");

        String email = jwtProvider.getPrincipal(accessToken);
        validatePayload(email);

        return authService.getUserIdByEmail(email);
    }

    private void validatePayload(String email) {
        if (email == null) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        }
    }

}