package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    @NotBlank(message = "댓글내용을 입력해주세요.")
    @Max(value = 300, message = "댓글은 300자 이상 입력할 수 없습니다.")
    private String content;

    public Comment convertDtoToEntity(CreateCommentRequestDto requestDto) {
        return Comment.builder()
                .schedule(Schedule.builder().seq(requestDto.getScheduleSeq()).build())
                .user(User.builder().seq(requestDto.getUserSeq()).build())
                .content(requestDto.getContent()).build();
    }
}
