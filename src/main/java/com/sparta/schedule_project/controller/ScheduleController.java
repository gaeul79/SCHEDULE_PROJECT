package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.ResponseStatusDto;
import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.service.ScheduleService;
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
@RequestMapping("/api.sparta.com")
public class ScheduleController {
    private ScheduleService scheduleService;

    /**
     * ScheduleService 객체를 의존성 주입 방식으로 받아옵니다.
     *
     * @param scheduleService 일정 관리 서비스
     */
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정 등록 API
     *
     * @param scheduleRequestDto 일정 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @PostMapping("/schedules")
    public ResponseEntity<ResponseStatusDto> createSchedules(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.OK);
    }

    /**
     * 일정 조회 API
     *
     * @param scheduleRequestDto 일정 조회 정보 (JSON 형태)
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @GetMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> searchSchedules(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.searchSchedule(scheduleRequestDto), HttpStatus.OK);
    }

    /**
     * 일정 수정 API
     *
     * @param scheduleRequestDto 일정 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ResponseStatusDto> updateSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleRequestDto), HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     *
     * @param scheduleRequestDto 일정 삭제 정보 (JSON 형태)
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<ResponseStatusDto> deleteSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.deleteSchedule(scheduleRequestDto), HttpStatus.OK);
    }
}
