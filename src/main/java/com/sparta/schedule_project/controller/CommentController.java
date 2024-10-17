package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.comment.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.RemoveCommentRequestDto;
import com.sparta.schedule_project.dto.request.comment.SearchCommentRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.comment.CommentResponseDto;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.sparta.com/schedules/{scheduleSeq}")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 등록 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 댓글 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @PostMapping("/comments")
    public ResponseEntity<ResponseStatusDto> createComment(HttpServletRequest req, @RequestBody CreateCommentRequestDto requestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(commentService.createComment(req, requestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 댓글 조회 API
     *
     * @param requestDto 댓글 조회 정보 (JSON 형태)
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
     * @since 2024-10-15
     */
    @GetMapping("/comments")
    public ResponseEntity<CommentResponseDto> searchComment(@RequestBody SearchCommentRequestDto requestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.searchComment(requestDto));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(CommentResponseDto.createResponseDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 댓글 수정 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 댓글 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @PutMapping("/comments/{commentSeq}")
    public ResponseEntity<ResponseStatusDto> updateComment(HttpServletRequest req, @RequestBody ModifyCommentRequestDto requestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.updateComment(req, requestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 댓글 삭제 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 댓글 삭제 정보 (JSON 형태)
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @DeleteMapping("/comments/{commentSeq}")
    public ResponseEntity<ResponseStatusDto> deleteComment(HttpServletRequest req, @RequestBody RemoveCommentRequestDto requestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.deleteComment(req, requestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }
}
