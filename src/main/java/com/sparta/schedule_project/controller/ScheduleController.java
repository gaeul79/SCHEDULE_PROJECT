package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedules(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("/schedules")
    public ScheduleResponseDto searchSchedules(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.searchSchedule(scheduleRequestDto);
    }

    @PutMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.updateSchedule(scheduleRequestDto);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto deleteSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.deleteSchedule(scheduleRequestDto);
    }
}
