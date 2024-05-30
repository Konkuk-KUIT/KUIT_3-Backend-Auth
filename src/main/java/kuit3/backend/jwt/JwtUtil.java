package kuit3.backend.jwt;

import jakarta.servlet.http.HttpServletRequest;
import kuit3.backend.common.exception.jwt.bad_request.JwtNoTokenException;
import kuit3.backend.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtExpiredTokenException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_TOKEN;
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    public String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateToken(token);
        return token.substring(JWT_TOKEN_PREFIX.length());
    }
//    public String resolveAccessToken(NativeWebRequest webRequest) {
//        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
//        validateToken(token);
//        return token.substring(JWT_TOKEN_PREFIX.length());
//    }

    public void validateToken(String token) {
        if (token == null) {
            throw new JwtNoTokenException(TOKEN_NOT_FOUND);
        }
        if (!token.startsWith(JWT_TOKEN_PREFIX)) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        }
    }

    public void validateAccessToken(String accessToken) {
        if (jwtProvider.isExpiredToken(accessToken)) {
            throw new JwtExpiredTokenException(EXPIRED_TOKEN);
        }
    }

    public void validatePayload(String email) {
        if (email == null) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        }
    }
    public String getEmail(String accessToken) {
        return jwtProvider.getPrincipal(accessToken);
    }
}
