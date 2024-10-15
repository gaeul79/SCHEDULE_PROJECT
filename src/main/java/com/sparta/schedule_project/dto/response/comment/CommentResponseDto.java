package com.sparta.schedule_project.dto.response.comment;

import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class CommentResponseDto {
    private Page<CommentDto> comments;
    private ResponseStatusDto responseStatusDto;

    private void setResponseStatusDto(ResponseStatusDto responseStatusDto) {
        this.responseStatusDto = responseStatusDto;
    }

    public void setResponseStatusDto(ResponseCode responseCode) {
        this.responseStatusDto = new ResponseStatusDto(responseCode);
    }

    public static CommentResponseDto createCommentResponseDto(Page<Comment> comments, ResponseCode responseCode) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
        responseScheduleDto.setComments(comments.map(CommentDto::from));
        responseScheduleDto.setResponseStatusDto(responseStatusDto);
        return responseScheduleDto;
    }

    public static CommentResponseDto createCommentResponseDto(Page<Comment> comments, ResponseCode responseCode, String message) {
        CommentResponseDto responseScheduleDto = new CommentResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, message);
        responseScheduleDto.setComments(comments.map(CommentDto::from));
        responseScheduleDto.setResponseStatusDto(responseStatusDto);
        return responseScheduleDto;
    }
}
