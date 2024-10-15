//package com.sparta.schedule_project.controller;
//
//import com.sparta.schedule_project.dto.response.ResponseStatusDto;
//import com.sparta.schedule_project.dto.request.create.CreateScheduleRequestDto;
//import com.sparta.schedule_project.dto.response.create.CreateScheduleResponseDto;
//import com.sparta.schedule_project.service.ScheduleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 일정 관리 API를 제공하는 컨트롤러 클래스
// *
// * @author 김현정
// * @since 2024-10-03
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api.sparta.com")
//public class ScheduleController {
//    private final ScheduleService scheduleService;
//
//    /**
//     * 일정 등록 API
//     *
//     * @param createScheduleRequestDto 일정 등록 정보 (JSON 형태)
//     * @return 등록 결과 (ResponseStatusDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    @PostMapping("/schedules")
//    public ResponseEntity<ResponseStatusDto> createSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(createScheduleRequestDto));
//    }
//
//    /**
//     * 일정 조회 API
//     *
//     * @param createScheduleRequestDto 일정 조회 정보 (JSON 형태)
//     * @return 조회 결과 (ScheduleResponseDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    @GetMapping("/schedules")
//    public ResponseEntity<CreateScheduleResponseDto> searchSchedules(@RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
//        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.searchSchedule(createScheduleRequestDto));
//    }
//
//    /**
//     * 일정 수정 API
//     *
//     * @param createScheduleRequestDto 일정 수정 정보 (JSON 형태)
//     * @return 수정 결과 (ResponseStatusDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    @PutMapping("/schedules/{scheduleId}")
//    public ResponseEntity<ResponseStatusDto> updateSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
//        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(createScheduleRequestDto));
//    }
//
//    /**
//     * 일정 삭제 API
//     *
//     * @param createScheduleRequestDto 일정 삭제 정보 (JSON 형태)
//     * @return 삭제 결과 (ResponseStatusDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    @DeleteMapping("/schedules/{scheduleId}")
//    public ResponseEntity<ResponseStatusDto> deleteSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
//        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.deleteSchedule(createScheduleRequestDto));
//    }
//}
