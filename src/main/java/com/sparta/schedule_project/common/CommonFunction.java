package com.sparta.schedule_project.common;

import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.common.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공통으로 사용할 수 있는 함수들을 모은 클래스
 *
 * @author 김현정 (수정 필요)
 * @since 2024-10-03
 */
@Service
@RequiredArgsConstructor
public class CommonFunction {
    /**
     * 멤버 번호로 유저를 조회합니다.
     *
     * @param seq 유저 seq
     * @return 검색된 회원
     * @throws ResponseException 검색된 유저가 없을시 발생하는 예외
     * @since 2024-10-23
     */
    public static User findUserBySeq(UserRepository userRepository, int seq) throws ResponseException {
        User user = userRepository.findBySeq(seq);
        if (user == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        return user;
    }

    /**
     * JWT 토큰의 사용자 정보가 본인이 맞는지 검증합니다.
     *
     * @param req       HttpServletRequest 객체
     * @param matchUser 검증할 사용자 정보
     * @throws ResponseException 토큰이 유효하지 않거나, 사용자가 존재하지 않거나, 권한이 부족한 경우 예외 발생
     * @since 2024-10-17
     */
    public static void matchCookie(HttpServletRequest req, User matchUser) throws ResponseException {
        User loginUser = (User) req.getAttribute("user");
        if (!loginUser.getEmail().equals(matchUser.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    /**
     * 쿠키에 저장된 사용자 정보를 추출합니다.
     *
     * @param req       HttpServletRequest 객체
     * @return 사용자 정보 객체 (User)
     * @since 2024-10-18
     */
    public static User getUserFromCookie(HttpServletRequest req) {
        return (User) req.getAttribute("user");
    }
}