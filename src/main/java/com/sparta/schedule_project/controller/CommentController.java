package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.response.CommentResponseDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 관리 API를 제공하는 컨트롤러 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{userId}/schedules/{scheduleId}")
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
    public ResponseEntity<ResponseStatusDto> createComment(
            HttpServletRequest req,
            @RequestBody CreateCommentRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(req, requestDto));
    }

    /**
     * 일정 댓글 검색 API
     *
     * @param req        HttpServletRequest 객체
     * @param scheduleId 댓글을 검색할 일정 번호
     * @param page       페이지 번호 (기본값: 1)
     * @param size       페이지당 항목 수 (기본값: 10)
     * @return 댓글 목록을 포함하는 ResponseEntity
     */
    @GetMapping("/comments")
    public ResponseEntity<CommentResponseDto> searchComment(
            HttpServletRequest req,
            @PathVariable int scheduleId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.searchComment(req, scheduleId, page - 1, size));
    }

    /**
     * 댓글 수정 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 댓글 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseStatusDto> updateComment(
            HttpServletRequest req,
            @RequestBody ModifyCommentRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(req, requestDto));
    }

    /**
     * 댓글 삭제 API
     *
     * @param req       HttpServletRequest 객체
     * @param commentId 삭제할 댓글 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseStatusDto> deleteComment(
            HttpServletRequest req,
            @PathVariable int commentId) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.deleteComment(req, commentId));
    }
}
