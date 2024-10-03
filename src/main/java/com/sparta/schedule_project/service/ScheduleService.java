package com.sparta.schedule_project.service;

import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.dto.ResponseStatusDto;
import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseStatusDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.createSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public ScheduleResponseDto searchSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleViewDto scheduleViewDto = ScheduleViewDto.from(scheduleRequestDto);
            List<ScheduleViewDto> schedules = scheduleRepository.searchSchedules(scheduleViewDto);
            return ScheduleResponseDto.from(schedules, ResponseCode.SUCCESS_SEARCH_SCHEDULE);
        } catch (Exception ex) {
            return ScheduleResponseDto.from(null,
                    ResponseCode.SUCCESS_SEARCH_SCHEDULE,
                    ex.getMessage());
        }
    }

    public ResponseStatusDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.updateSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public ResponseStatusDto deleteSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            scheduleRepository.deleteSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
