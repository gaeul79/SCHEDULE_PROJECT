package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.ResponseStatusDto;
import com.sparta.schedule_project.dto.ScheduleRequestDto;
import com.sparta.schedule_project.dto.ScheduleResponseDto;
import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 일정 관리 서비스 클래스
 *
 * @author 김현정 (수정 필요)
 * @since 2024-10-03
 */
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    /**
     * ScheduleRepository 객체를 의존성 주입 방식으로 받아옵니다.
     *
     * @param scheduleRepository 일정 레포지토리
     * @author 김현정
     * @since 2024-10-03
     */
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 일정 생성
     *
     * @param scheduleRequestDto 일정 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
//            ScheduleDto scheduleDto = ScheduleDto.from(scheduleRequestDto);
            ScheduleDto scheduleDto = new ScheduleDto(scheduleRequestDto);
            scheduleRepository.createSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 일정을 조회
     *
     * @param scheduleRequestDto 일정 조회 요청 정보
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ScheduleResponseDto searchSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            ScheduleViewDto scheduleViewDto = new ScheduleViewDto(scheduleRequestDto);
            Integer totalCount = scheduleRepository.searchScheduleCount(scheduleViewDto);
            List<ScheduleViewDto> schedules = Optional.of(totalCount).orElse(0) > 0 ?
                    scheduleRepository.searchSchedules(scheduleViewDto) : null;

            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto();
            scheduleResponseDto.setTotalCount(totalCount);
            scheduleResponseDto.setSchedule(schedules);
            scheduleResponseDto.setResponseStatusDto(ResponseCode.SUCCESS_SEARCH_SCHEDULE);

            return scheduleResponseDto;
        } catch (Exception ex) {
            return ScheduleResponseDto.from(null,
                    ResponseCode.UNKNOWN_ERROR,
                    ex.getMessage());
        }
    }

    /**
     * 일정 수정
     *
     * @param scheduleRequestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            checkAccess(scheduleRequestDto);
            ScheduleDto scheduleDto = new ScheduleDto(scheduleRequestDto);
            scheduleRepository.updateSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE);
        } catch (ResponseException ex) {
            return new ResponseStatusDto(ex.getResponseCode());
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 일정 삭제
     *
     * @param scheduleRequestDto 일정 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto deleteSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {
            checkAccess(scheduleRequestDto);
            ScheduleDto scheduleDto = new ScheduleDto(scheduleRequestDto);
            scheduleRepository.deleteSchedule(scheduleDto);
            return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE);
        } catch (ResponseException ex) {
            return new ResponseStatusDto(ex.getResponseCode());
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 일정 수정 요청에 대한 권한을 검사합니다.
     *
     * @param scheduleRequestDto 일정 수정 요청 DTO
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @author 김현정
     * @since 2024-10-04
     */
    private void checkAccess(ScheduleRequestDto scheduleRequestDto) throws ResponseException {
        if(!scheduleRequestDto.getLoginUserId().equals(scheduleRequestDto.getUserId()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
