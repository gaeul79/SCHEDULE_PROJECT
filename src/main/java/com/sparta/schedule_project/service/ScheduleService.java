package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.dto.StatusDto;
import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.dto.entity.UserDto;
import com.sparta.schedule_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public StatusDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.createSchedule(scheduleDto);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public ScheduleResponseDto searchSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleViewDto scheduleViewDto = ScheduleViewDto.from(scheduleRequestDto);
            List<ScheduleViewDto> schedules = scheduleRepository.searchSchedules(scheduleViewDto);
            StatusDto statusDto = new StatusDto(200, "Success");
            return ScheduleResponseDto.from(schedules, statusDto);
        } catch (Exception ex) {
            StatusDto statusDto = new StatusDto(999, "Failed");
            return ScheduleResponseDto.from(null, statusDto);
        }
    }

    public StatusDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.updateSchedule(scheduleDto);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public StatusDto deleteSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.deleteSchedule(scheduleDto);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }
}
