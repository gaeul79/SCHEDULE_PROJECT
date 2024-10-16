package com.sparta.schedule_project.dto.response.comment;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

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

    public static CommentResponseDto createResponseDto(ResponseCode responseCode) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        responseScheduleDto.setStatus(responseCode);
        return responseScheduleDto;
    }

    public static CommentResponseDto createResponseDto(Page<Comment> comments, ResponseCode responseCode) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
        responseScheduleDto.setComments(comments.stream().map(CommentDto::from).toList());
        responseScheduleDto.setPage(new PageDto(comments.getPageable(), comments.getTotalPages()));
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }

    public static CommentResponseDto createResponseDto(ResponseCode responseCode, String message) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, message);
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }
}
