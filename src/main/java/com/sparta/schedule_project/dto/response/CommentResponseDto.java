package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 댓글 목록 조회 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 댓글 목록 조회 결과를 전달할 때 사용됩니다.
 *
 * @since 2024-10-18
 */
@Data
@NoArgsConstructor
public class CommentResponseDto {
    private List<CommentDto> comments;
    private PageDto page;
    private ResponseStatusDto status;

    private void setStatus(ResponseStatusDto status) {
        this.status = status;
    }

    public void setStatus(ResponseCode responseCode) {
        this.status = new ResponseStatusDto(responseCode);
    }

    /**
     * 응답 상태 정보를 설정합니다. (ResponseCode 객체 사용)
     *
     * @param responseCode 응답 코드
     * @since 2024-10-18
     */
    public static CommentResponseDto createResponseDto(ResponseCode responseCode) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        responseScheduleDto.setStatus(responseCode);
        return responseScheduleDto;
    }

    /**
     * 댓글 목록 조회 결과를 기반으로 응답 DTO를 생성합니다.
     * 댓글 목록과 페이징 정보, 응답 코드를 포함합니다.
     *
     * @param comments     조회된 댓글 목록 (Page<Comment>)
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (CommentResponseDto)
     * @since 2024-10-18
     */
    public static CommentResponseDto createResponseDto(Page<Comment> comments, ResponseCode responseCode) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
        responseScheduleDto.setComments(comments.stream().map(CommentDto::from).toList());
        responseScheduleDto.setPage(new PageDto(comments.getPageable(), comments.getTotalPages()));
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }

    /**
     * 응답 코드만 포함하는 응답 DTO를 생성합니다.
     *
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (CommentResponseDto)
     * @since 2024-10-18
     */
    public static CommentResponseDto createResponseDto(ResponseCode responseCode, String message) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, message);
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }
}
