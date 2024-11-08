package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.response.CommentDto;
import com.sparta.schedule_project.dto.response.PageResponseDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.repository.CommentRepository;
import com.sparta.schedule_project.util.login.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     *
     * @param user       로그인 유저
     * @param requestDto 댓글 등록 요청 정보
     * @return 생성 결과
     * @since 2024-10-15
     */
    public ResponseDto<CommentDto> createComment(User user, CreateCommentRequestDto requestDto) {
        Comment comment = requestDto.convertDtoToEntity(user);
        commentRepository.save(comment);
        return ResponseDto.of(CommentDto.from(comment));
    }

    /**
     * 댓글 조회
     *
     * @param scheduleId 댓글을 검색할 일정 번호
     * @param page       페이지 번호 (기본값: 1)
     * @param size       페이지당 항목 수 (기본값: 10)
     * @return 조회 결과
     * @since 2024-10-15
     */
    public PageResponseDto<CommentDto> searchComment(int scheduleId, int page, int size) {
        Page<Comment> comments = commentRepository
                .findAllByScheduleIdOrderByUpdatedAtDesc(scheduleId, PageRequest.of(page, size));
        return PageResponseDto.of(
                comments.map(CommentDto::from).toList(),
                comments.getPageable(),
                comments.getTotalPages()
        );
    }

    /**
     * 댓글 수정
     *
     * @param user       로그인 유저
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과
     * @since 2024-10-15
     */
    @Transactional
    public ResponseDto<CommentDto> updateComment(User user, ModifyCommentRequestDto requestDto) throws BusinessException {
        Comment comment = commentRepository.findById(requestDto.getCommentId());
        validateAuth(user, comment);
        comment.update(requestDto);
        return ResponseDto.of(CommentDto.from(comment));
    }

    /**
     * 댓글 삭제
     *
     * @param user      로그인 유저
     * @param commentId 삭제할 댓글 id
     * @since 2024-10-15
     */
    public void deleteComment(User user, int commentId) throws BusinessException {
        Comment comment = commentRepository.findById(commentId);
        validateAuth(user, comment);
        commentRepository.delete(comment);
    }

    /**
     * 댓글 수정/삭제 요청에 대한 권한을 검사합니다.
     *
     * @param user    로그인한 사용자 정보
     * @param comment 요청이 들어온 댓글 정보
     * @throws BusinessException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void validateAuth(User user, Comment comment) throws BusinessException {
        if (comment == null)
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        if (user.getAuth() != AuthType.ADMIN && user.getId() != comment.getUser().getId())
            throw new BusinessException(ErrorCode.INVALID_PERMISSION);
    }
}
