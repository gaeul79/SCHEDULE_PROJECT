package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.response.PageResponseDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.dto.response.ScheduleDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.service.ScheduleService;
import com.sparta.schedule_project.util.login.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 일정 관리 API를 제공하는 컨트롤러 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{userId}")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 등록 API
     *
     * @param user       로그인 유저
     * @param requestDto 일정 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @PostMapping("/schedules")
    public ResponseEntity<ResponseDto<ScheduleDto>> createSchedule(
            @LoginUser User user,
            @RequestBody @Valid CreateScheduleRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.createSchedule(user, requestDto));
    }

    /**
     * 일정 검색 API
     *
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지당 항목 수 (기본값: 10)
     * @return 일정 목록을 포함하는 ResponseEntity
     * @since 2024-10-29
     */
    @GetMapping("/schedules")
    public ResponseEntity<PageResponseDto<ScheduleDto>> searchSchedule(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.searchSchedule(page - 1, size));
    }

    /**
     * 일정 수정 API
     *
     * @param user       로그인 유저
     * @param requestDto 일정 수정 정보 (JSON 형태)
     * @return 수정 결과
     * @since 2024-10-03
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ResponseDto<ScheduleDto>> updateSchedule(
            @LoginUser User user,
            @RequestBody @Valid ModifyScheduleRequestDto requestDto) throws BusinessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.updateSchedule(user, requestDto));
    }

    /**
     * 일정 삭제 API
     *
     * @param user       로그인 유저
     * @param scheduleId 삭제할 일정 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @LoginUser User user,
            @PathVariable int scheduleId) throws BusinessException {
        scheduleService.deleteSchedule(user, scheduleId);
        return ResponseEntity.noContent().build();
    }
}
