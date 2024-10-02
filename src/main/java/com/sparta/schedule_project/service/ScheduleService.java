package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleDto schedule = new ScheduleDto();
        ScheduleDto resultSchedule = scheduleRepository.createSchedule(schedule);
        ScheduleResponseDto response = new ScheduleResponseDto();
        return response;
    }

    public ScheduleResponseDto searchSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleDto schedule = new ScheduleDto();
        List<ScheduleViewDto> schedules = scheduleRepository.searchScheduleView(schedule);
        ScheduleResponseDto response = new ScheduleResponseDto();
        return response;
    }

    public ScheduleResponseDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleRepository.updateSchedule(scheduleDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        return responseDto;
    }

    public ScheduleResponseDto deleteSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleRepository.deleteSchedule(scheduleDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        return responseDto;
    }
}
