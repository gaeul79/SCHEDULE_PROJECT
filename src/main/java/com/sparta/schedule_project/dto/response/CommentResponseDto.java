package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.entity.Comment;
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

    /**
     * 댓글 목록 조회 결과를 기반으로 응답 DTO를 생성합니다.
     * 댓글 목록과 페이징 정보, 응답 코드를 포함합니다.
     *
     * @param comments     조회된 댓글 목록 (Page<Comment>)
     * @param requestUrl   요청 url
     * @return 생성된 응답 DTO 객체 (CommentResponseDto)
     * @since 2024-10-18
     */
    public static CommentResponseDto createResponseDto(Page<Comment> comments, String requestUrl) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(ResponseCode.SUCCESS_SEARCH_COMMENT, requestUrl);
        responseScheduleDto.setComments(comments.stream().map(CommentDto::from).toList());
        responseScheduleDto.setPage(new PageDto(comments.getPageable(), comments.getTotalPages()));
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }
}
