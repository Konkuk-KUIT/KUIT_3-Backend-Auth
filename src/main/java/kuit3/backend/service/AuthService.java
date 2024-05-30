package kuit3.backend.service;

import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtUnauthorizedTokenException;
import kuit3.backend.dao.UserDao;
import kuit3.backend.dto.auth.LoginRequest;
import kuit3.backend.dto.auth.LoginResponse;
import kuit3.backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest authRequest) {
        log.info("[AuthService.login]");

        String email = authRequest.getEmail();

        // TODO: 1. 이메일 유효성 확인
        long userid;
        try {
            userid = userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserException(EMAIL_NOT_FOUND);
        }

        // TODO: 2. 비밀번호 일치 확인
        validatePassword(authRequest.getPassword(), userid);

        // TODO: 3. JWT 갱신
        String updatedJwt = jwtTokenProvider.createToken(email, userid);

        return new LoginResponse(userid, updatedJwt);
    }

    private void validatePassword(String password, long userid) {
        String encodedPassword = userDao.getPasswordByUserId(userid);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    public long getUserIdByEmail(String email) {
        try {
            return userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new JwtUnauthorizedTokenException(TOKEN_MISMATCH);
        }
    }

}