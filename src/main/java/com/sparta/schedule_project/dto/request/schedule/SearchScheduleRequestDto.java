package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.dto.request.comment.SearchCommentRequestDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Pageable;
import java.time.LocalDate;

/**
 * 일정 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Getter
@NoArgsConstructor
public class SearchScheduleRequestDto {
    private String userName;
    private String title;
    private LocalDate startUpdateDate;
    private LocalDate endUpdateDate;

    @NotBlank(message = "페이지는 공백일 수 없습니다.")
    private Pageable page;

    public static Schedule to(SearchScheduleRequestDto scheduleDto) {
        return Schedule.builder()
                .user(User.builder().name(scheduleDto.getUserName()).build())
                .title(scheduleDto.getTitle())
                .startUpdateDate(scheduleDto.getStartUpdateDate())
                .endUpdateDate(scheduleDto.getEndUpdateDate())
                .page(scheduleDto.getPage()).build();
    }
}
