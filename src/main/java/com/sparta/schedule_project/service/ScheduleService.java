package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.response.PageResponseDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.dto.response.ScheduleDto;
import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.repository.ScheduleRepository;
import com.sparta.schedule_project.util.WeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 일정 관리 서비스 클래스
 *
 * @author 김현정 (수정 필요)
 * @since 2024-10-03
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final WeatherApi weatherApi;
    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     *
     * @param user       로그인 유저
     * @param requestDto 일정 등록 요청 정보
     * @return 생성 결과
     * @since 2024-10-03
     */
    public ResponseDto<ScheduleDto> createSchedule(User user, CreateScheduleRequestDto requestDto) {
        String weather = weatherApi.getTodayWeather();
        Schedule schedule = requestDto.convertDtoToEntity(user.getId(), weather);
        scheduleRepository.save(schedule);
        return ResponseDto.of(ScheduleDto.from(schedule));
    }

    /**
     * 일정을 조회
     *
     * @param page 페이지 번호
     * @param size 페이지당 항목 수
     * @return 조회 결과
     * @since 2024-10-03
     */
    public PageResponseDto<ScheduleDto> searchSchedule(int page, int size) {
        Page<Schedule> schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(page, size));
        return PageResponseDto.of(
                schedules.map(ScheduleDto::from).toList(),
                schedules.getPageable(),
                schedules.getTotalPages());
    }

    /**
     * 일정 수정
     *
     * @param user       로그인 유저
     * @param requestDto 수정할 일정 내용
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseDto<ScheduleDto> updateSchedule(User user, ModifyScheduleRequestDto requestDto) throws BusinessException {
        String weather = weatherApi.getTodayWeather();
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId());
        validateAuth(user, schedule);
        schedule.update(requestDto, weather);
        return ResponseDto.of(ScheduleDto.from(schedule));
    }

    /**
     * 일정 삭제
     *
     * @param user       로그인 유저
     * @param scheduleId 삭제할 일정 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseDto<ScheduleDto> deleteSchedule(User user, int scheduleId) throws BusinessException {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        validateAuth(user, schedule);
        scheduleRepository.delete(schedule);
        return ResponseDto.of(ScheduleDto.from(schedule));
    }

    /**
     * 일정 [수정/삭제] 요청에 대한 권한을 검사합니다.
     *
     * @param loginUser 로그인한 사용자 정보
     * @param schedule  [수정/삭제] 요청이 들어온 일정 정보
     * @throws BusinessException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void validateAuth(User loginUser, Schedule schedule) throws BusinessException {
        if (schedule == null)
            throw new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND);
        if (loginUser.getAuth() != AuthType.ADMIN && loginUser.getId() != schedule.getUser().getId())
            throw new BusinessException(ErrorCode.INVALID_PERMISSION);
    }
}
