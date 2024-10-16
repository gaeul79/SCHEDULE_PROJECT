package com.sparta.schedule_project.service;

import com.sparta.schedule_project.common.AuthType;
import com.sparta.schedule_project.common.TestData;
import com.sparta.schedule_project.dto.request.comment.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.RemoveCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.SearchCommentRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.comment.CommentResponseDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     *
     * @param requestDto 댓글 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-15
     */
    public ResponseStatusDto createComment(CreateCommentRequestDto requestDto) {
        Comment comment = CreateCommentRequestDto.convertDtoToEntity(requestDto);
        commentRepository.save(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_COMMENT);
    }

    /**
     * 댓글 조회
     *
     * @param requestDto 댓글 조회 요청 정보
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
     * @since 2024-10-15
     */
    public CommentResponseDto searchComment(SearchCommentRequestDto requestDto) {
        Comment comment = SearchCommentRequestDto.convertDtoToEntity(requestDto);
        Page<Comment> comments = commentRepository.findAllByScheduleSeqOrderByUpdateDateDesc(comment.getSchedule().getSeq(), comment.getPage());
        return CommentResponseDto.createResponseDto(comments, ResponseCode.SUCCESS_SEARCH_COMMENT);
    }

    /**
     * 댓글 수정
     *
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-15
     */
    @Transactional
    public ResponseStatusDto updateComment(ModifyCommentRequestDto requestDto) throws ResponseException {
        Comment updateInfo = ModifyCommentRequestDto.convertDtoToEntity(requestDto);
        Comment comment = commentRepository.findBySeq(updateInfo.getSeq());
        checkAccess(comment);
        comment.update(updateInfo);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_COMMENT);
    }

    /**
     * 댓글 삭제
     *
     * @param requestDto 댓글 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-15
     */
    public ResponseStatusDto deleteSchedule(RemoveCommentRequestDto requestDto) throws ResponseException {
        Comment comment = RemoveCommentRequestDto.convertDtoToEntity(requestDto);
        checkAccess(comment);
        commentRepository.delete(comment);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_COMMENT);
    }

    /**
     * 댓글 수정/삭제 요청에 대한 권한을 검사합니다.
     *
     * @param comment 일정 수정 권한
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @author 김현정
     * @since 2024-10-15
     */
    private void checkAccess(Comment comment) throws ResponseException {
        if(comment == null)
            throw new ResponseException(ResponseCode.COMMENT_NOT_FOUND);
        else if(TestData.testAuth != AuthType.ADMIN)
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        else if (TestData.testSeq != comment.getUser().getSeq())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
