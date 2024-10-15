package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.comment.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.RemoveCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.SearchCommentRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.comment.CommentResponseDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
        try {
            Comment comment = CreateCommentRequestDto.to(requestDto);
            commentRepository.save(comment);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
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
        try {
            Comment comment = SearchCommentRequestDto.to(requestDto);
            Page<Comment> comments = commentRepository.findAllByScheduleOrderByUpdateDateDesc(comment.getSchedule(), comment.getPage());
            return CommentResponseDto.createCommentResponseDto(comments, ResponseCode.SUCCESS_SEARCH_SCHEDULE);
        } catch (Exception ex) {
            return CommentResponseDto.createCommentResponseDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 댓글 수정
     *
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-15
     */
    public ResponseStatusDto updateComment(ModifyCommentRequestDto requestDto) {
        try {
            Comment updateInfo = ModifyCommentRequestDto.to(requestDto);
            Comment schedule = commentRepository.findBySeq(updateInfo.getSeq());
            schedule.update(updateInfo);
            return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE);
        }
//        catch (ResponseException ex) {
//            return new ResponseStatusDto(ex.getResponseCode());
//        }
        catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 댓글 삭제
     *
     * @param requestDto 댓글 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-15
     */
    public ResponseStatusDto deleteSchedule(RemoveCommentRequestDto requestDto) {
        try {
//            checkAccess(requestDto);
            Comment comment = RemoveCommentRequestDto.to(requestDto);
            commentRepository.delete(comment);
            return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE);
        }
//        catch (ResponseException ex)
//        {
//            return new ResponseStatusDto(ex.getResponseCode());
//        }
        catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
