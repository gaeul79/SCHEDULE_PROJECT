package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.schedule.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.RemoveScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.SearchScheduleRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.schedule.ScheduleResponseDto;
import com.sparta.schedule_project.service.ScheduleService;
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
@RequestMapping("/api.sparta.com")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 등록 API
     *
     * @param requestDto 일정 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @PostMapping("/schedules")
    public ResponseEntity<ResponseStatusDto> createSchedule(@RequestBody CreateScheduleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(requestDto));
    }

    /**
     * 일정 조회 API
     *
     * @param requestDto 일정 조회 정보 (JSON 형태)
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @GetMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> searchSchedules(@RequestBody SearchScheduleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.searchSchedule(requestDto));
    }

    /**
     * 일정 수정 API
     *
     * @param requestDto 일정 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ResponseStatusDto> updateSchedule(@RequestBody ModifyScheduleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(requestDto));
    }

    /**
     * 일정 삭제 API
     *
     * @param requestDto 일정 삭제 정보 (JSON 형태)
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<ResponseStatusDto> deleteSchedule(@RequestBody RemoveScheduleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.deleteSchedule(requestDto));
    }
}
