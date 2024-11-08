package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateCommentRequestDto;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import com.sparta.schedule_project.dto.response.CommentDto;
import com.sparta.schedule_project.dto.response.PageResponseDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.service.CommentService;
import com.sparta.schedule_project.util.login.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
     * @param user       로그인 유저
     * @param requestDto 댓글 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @PostMapping("/comments")
    public ResponseEntity<ResponseDto<CommentDto>> createComment(
            @LoginUser User user,
            @RequestBody @Valid CreateCommentRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(user, requestDto));
    }

    /**
     * 일정 댓글 검색 API
     *
     * @param scheduleId 댓글을 검색할 일정 번호
     * @param page       페이지 번호 (기본값: 1)
     * @param size       페이지당 항목 수 (기본값: 10)
     * @return 댓글 목록을 포함하는 ResponseEntity
     */
    @GetMapping("/comments")
    public ResponseEntity<PageResponseDto<CommentDto>> searchComment(
            HttpServletRequest req,
            @PathVariable int scheduleId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.searchComment(scheduleId, page - 1, size));
    }

    /**
     * 댓글 수정 API
     *
     * @param user       로그인 유저
     * @param requestDto 댓글 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentDto>> updateComment(
            @LoginUser User user,
            @RequestBody @Valid ModifyCommentRequestDto requestDto) throws BusinessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(user, requestDto));
    }

    /**
     * 댓글 삭제 API
     *
     * @param user      로그인 유저
     * @param commentId 삭제할 댓글 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-15
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @LoginUser User user,
            @PathVariable int commentId) throws BusinessException {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.noContent().build();
    }
}
