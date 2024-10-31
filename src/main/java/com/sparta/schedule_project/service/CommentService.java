package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.response.CommentResponseDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.CommentRepository;
import com.sparta.schedule_project.util.token.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;

    /**
     * 댓글 생성
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 댓글 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    public ResponseStatusDto createComment(HttpServletRequest req, CreateCommentRequestDto requestDto) {
        User user = tokenProvider.getUser(req);
        Comment comment = requestDto.convertDtoToEntity(user);
        commentRepository.save(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_COMMENT, req.getRequestURI());
    }

    /**
     * 댓글 조회
     *
     * @param scheduleId 댓글을 검색할 일정 번호
     * @param req        HttpServletRequest 객체
     * @param page       페이지 번호 (기본값: 1)
     * @param size       페이지당 항목 수 (기본값: 10)
     * @return 조회 결과 (ScheduleResponseDto)
     * @since 2024-10-15
     */
    public CommentResponseDto searchComment(HttpServletRequest req, int scheduleId, int page, int size) {
        Page<Comment> comments = commentRepository
                .findAllByScheduleIdOrderByUpdatedAtDesc(scheduleId, PageRequest.of(page, size));
        return CommentResponseDto.createResponseDto(comments, req.getRequestURI());
    }

    /**
     * 댓글 수정
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @Transactional
    public ResponseStatusDto updateComment(HttpServletRequest req, ModifyCommentRequestDto requestDto) throws ResponseException {
        User user = tokenProvider.getUser(req);
        Comment comment = commentRepository.findById(requestDto.getCommentId());
        validateAuth(user, comment);
        comment.update(requestDto);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_COMMENT, req.getRequestURI());
    }

    /**
     * 댓글 삭제
     *
     * @param req       HttpServletRequest 객체
     * @param commentId 삭제할 댓글 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    public ResponseStatusDto deleteComment(HttpServletRequest req, int commentId) throws ResponseException {
        User user = tokenProvider.getUser(req);
        Comment comment = commentRepository.findById(commentId);
        validateAuth(user, comment);
        commentRepository.delete(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_COMMENT, req.getRequestURI());
    }

    /**
     * 댓글 수정/삭제 요청에 대한 권한을 검사합니다.
     *
     * @param user    로그인한 사용자 정보
     * @param comment 요청이 들어온 댓글 정보
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void validateAuth(User user, Comment comment) throws ResponseException {
        if (comment == null)
            throw new ResponseException(ResponseCode.COMMENT_NOT_FOUND);
        if (user.getAuth() != AuthType.ADMIN && user.getId() != comment.getUser().getId())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
