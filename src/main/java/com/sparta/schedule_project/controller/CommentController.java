package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.request.RemoveCommentRequestDto;
import com.sparta.schedule_project.dto.request.SearchCommentRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.CommentResponseDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.sparta.com/{userSeq}/schedules/{scheduleSeq}")
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
    public ResponseEntity<ResponseStatusDto> createComment(HttpServletRequest req, @RequestBody CreateCommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(req, requestDto));
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.searchComment(requestDto));
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
    public ResponseEntity<ResponseStatusDto> updateComment(HttpServletRequest req, @RequestBody ModifyCommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(req, requestDto));
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
    public ResponseEntity<ResponseStatusDto> deleteComment(HttpServletRequest req, @RequestBody RemoveCommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.deleteComment(req, requestDto));
    }
}
