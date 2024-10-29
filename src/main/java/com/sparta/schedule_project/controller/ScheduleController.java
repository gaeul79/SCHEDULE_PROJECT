package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.request.RemoveScheduleRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.ScheduleResponseDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api.sparta.com/{userSeq}")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 등록 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 등록 정보 (JSON 형태)
     * @return 등록 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @PostMapping("/schedules")
    public ResponseEntity<ResponseStatusDto> createSchedule(
            HttpServletRequest req,
            @RequestBody CreateScheduleRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.createSchedule(req, requestDto));
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
    public ResponseEntity<ScheduleResponseDto> searchSchedules(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.searchSchedule(page, size));
    }

    /**
     * 일정 수정 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 수정 정보 (JSON 형태)
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @PutMapping("/schedules/{scheduleSeq}")
    public ResponseEntity<ResponseStatusDto> updateSchedule(
            HttpServletRequest req,
            @RequestBody ModifyScheduleRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.updateSchedule(req, requestDto));
    }

    /**
     * 일정 삭제 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 삭제 정보 (JSON 형태)
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @DeleteMapping("/schedules/{scheduleSeq}")
    public ResponseEntity<ResponseStatusDto> deleteSchedule(
            HttpServletRequest req,
            @RequestBody RemoveScheduleRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.deleteSchedule(req, requestDto));
    }
}
