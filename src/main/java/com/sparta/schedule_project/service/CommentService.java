package com.sparta.schedule_project.service;

import com.sparta.schedule_project.cookie.AuthType;
import com.sparta.schedule_project.cookie.CookieManager;
import com.sparta.schedule_project.dto.request.comment.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.RemoveCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.SearchCommentRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.comment.CommentResponseDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.CommentRepository;
import com.sparta.schedule_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CookieManager cookieManager;

    /**
     * 댓글 생성
     *
     * @param token      요청 헤더의 쿠키에 담긴 인증 토큰
     * @param requestDto 댓글 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    public ResponseStatusDto createComment(String token, CreateCommentRequestDto requestDto) throws ResponseException {
        User user = getLoginUserInfo(token);
        Comment comment = requestDto.convertDtoToEntity(requestDto, user);
        commentRepository.save(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_COMMENT);
    }

    /**
     * 댓글 조회
     *
     * @param requestDto 댓글 조회 요청 정보
     * @return 조회 결과 (ScheduleResponseDto)
     * @since 2024-10-15
     */
    public CommentResponseDto searchComment(SearchCommentRequestDto requestDto) {
        Comment comment = requestDto.convertDtoToEntity(requestDto);
        Page<Comment> comments = commentRepository.findAllByScheduleSeqOrderByUpdateDateDesc(comment.getSchedule().getSeq(), comment.getPage());
        return CommentResponseDto.createResponseDto(comments, ResponseCode.SUCCESS_SEARCH_COMMENT);
    }

    /**
     * 댓글 수정
     *
     * @param token      요청 헤더의 쿠키에 담긴 인증 토큰
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @Transactional
    public ResponseStatusDto updateComment(String token, ModifyCommentRequestDto requestDto) throws ResponseException {
        Comment updateComment = requestDto.convertDtoToEntity(requestDto);
        Comment comment = commentRepository.findBySeq(updateComment.getSeq());
        checkValue(token, comment);
        comment.update(updateComment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_COMMENT);
    }

    /**
     * 댓글 삭제
     *
     * @param token      요청 헤더의 쿠키에 담긴 인증 토큰
     * @param requestDto 댓글 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    public ResponseStatusDto deleteSchedule(String token, RemoveCommentRequestDto requestDto) throws ResponseException {
        Comment comment = requestDto.convertDtoToEntity(requestDto);
        checkValue(token, comment);
        commentRepository.delete(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_COMMENT);
    }

    /**
     * 댓글 수정/삭제 요청에 대한 권한을 검사합니다.
     *
     * @param token   요청 헤더의 쿠키에 담긴 인증 토큰
     * @param comment 요청이 들어온 댓글 정보
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void checkValue(String token, Comment comment) throws ResponseException {
        User loginUser = getLoginUserInfo(token);
        if (comment == null)
            throw new ResponseException(ResponseCode.COMMENT_NOT_FOUND);
        else if (loginUser.getAuth() != AuthType.ADMIN ||
                loginUser.getSeq() != comment.getUser().getSeq())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    /**
     * JWT 토큰에서 로그인 사용자 정보를 추출하고 검증합니다.
     *
     * @param token JWT 토큰 값
     * @return 로그인 사용자 정보 (User 객체)
     * @throws ResponseException 토큰이 유효하지 않거나 사용자가 존재하지 않는 경우 예외 발생
     * @since 2024-10-17
     */
    private User getLoginUserInfo(String token) throws ResponseException {
        User loginUser = cookieManager.getUserFromJwtToken(token);
        User searchUser = userRepository.findByEmail(loginUser.getEmail());
        if (searchUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);

        if (!loginUser.getEmail().equals(searchUser.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        return loginUser;
    }
}
