package com.sparta.schedule_project.util.login;

import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Jwt (JSON Web Token) 유틸리티 클래스입니다.
 *
 * @since 2024-10-18
 */
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * Jwt 토큰을 생성합니다.
     *
     * @param email 사용자 이메일
     * @param auth  사용자 권한
     * @return 생성된 JWT 토큰 (String)
     * @since 2024-10-18
     */
    public String createToken(String email, AuthType auth) {
        LocalDateTime now = LocalDateTime.now();
        long TOKEN_TIME = 60 * 60; // 토큰 만료시간 60분
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email) // 사용자 식별자 값(ID)
                        .claim(AUTHORIZATION_KEY, auth) // 사용자 권한
                        .setIssuedAt(Timestamp.valueOf(now)) // 발급일
                        .setExpiration(Timestamp.valueOf(now.plusSeconds(TOKEN_TIME))) // 만료 시간
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    /**
     * 쿠키에 저장된 토큰 값에서 토큰 자체만 추출합니다.
     * (토큰 앞에 붙은 "Bearer " 문자열 제거)
     *
     * @param tokenValue 쿠키에서 읽어온 토큰 값 (String)
     * @return 토큰 자체 값 (String)
     * @throws NullPointerException 토큰 값이 유효하지 않거나 없는 경우 발생
     */
    public String substringToken(String tokenValue) throws BusinessException {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new BusinessException(ErrorCode.TOKEN_INVALID);
    }

    /**
     * 유효한 토큰에서 사용자 정보를 추출합니다.
     *
     * @param token 유효한 JWT 토큰 (String)
     * @return 토큰에 포함된 사용자 정보 (Claims 객체)
     * @since 2024-10-18
     */
    private Claims parseClaimsJws(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * JWT 토큰을 파싱하여 클레임 정보를 추출합니다.
     *
     * @param token JWT 토큰 문자열
     * @return 토큰의 클레임 정보
     * @throws BusinessException 토큰이 유효하지 않은 경우 예외 발생
     * @since 2024-10-31
     */
    public Claims getClaims(String token) {
        if (!StringUtils.hasText(token)) // 토큰 확인
            throw new BusinessException(ErrorCode.TOKEN_NOT_FOUND);

        token = substringToken(token); // JWT 토큰 substring
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return parseClaimsJws(token); // 토큰에서 사용자 정보 가져오기
    }
}
