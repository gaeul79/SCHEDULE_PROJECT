package com.sparta.schedule_project.dto.response.schedule;

import com.sparta.schedule_project.dto.response.comment.CommentDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private int seq;
    private int userSeq;
    private String userName;
    private String title;
    private String content;
    private String weather;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // TODO. khj 여기서 User select 쿼리 날아가는지 테스트 해볼 것.
    public static ScheduleDto from(Schedule schedule) {
        return ScheduleDto.builder().seq(schedule.getSeq())
                .userSeq(schedule.getUser().getSeq())
                .userName(schedule.getUser().getName())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .weather(schedule.getWeather())
                .createDate(schedule.getCreateDate())
                .updateDate(schedule.getUpdateDate()).build();
    }
}
