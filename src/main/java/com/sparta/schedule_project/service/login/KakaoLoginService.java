package com.sparta.schedule_project.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.emums.SocialType;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.repository.UserRepository;
import com.sparta.schedule_project.util.token.TokenProvider;
import com.sparta.schedule_project.util.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


/**
 * Kakao Social Login 기능을 제공하는 서비스
 *
 * @since 2024-10-31
 */
@Service
@RequiredArgsConstructor
public class KakaoLoginService implements SocialLogin {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final TokenProvider tokenProvider;

    @Override
    public ResponseStatusDto login(HttpServletRequest req, HttpServletResponse res, String accessCode) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(accessCode);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        User user = getUser(accessToken);

        // 3. 필요시에 회원가입
        signUp(user);

        // 4. 토큰 추가
        tokenProvider.setToken(res, user);

        // 5. 반환
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN, req.getRequestURI());
    }

    @Override
    public String getToken(String accessCode) throws JsonProcessingException {
        // log.info("인가코드: " + code);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "fec5298e3f8dd5a3c3ecd134fde3dc80");
        body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
        body.add("code", accessCode);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    @Override
    public User getUser(String accessToken) throws JsonProcessingException {
        // log.info("accessToken: " + accessToken);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return User.builder()
                .email(email)
                .name(nickname)
                .build();
    }

    @Override
    public void signUp(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser != null) {
            // 기존 회원정보에 카카오 Id 추가
            user.updateSocialType(SocialType.KAKAO);
        } else {
            // 신규 회원가입
            // password: random UUID - 기존 로그인으로 로그인되지 않도록
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            user.updatePassword(encodedPassword);
        }

        userRepository.save(user);
    }
}
