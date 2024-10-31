package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.ScheduleResponseDto;
import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.infra.WeatherApi;
import com.sparta.schedule_project.repository.ScheduleRepository;
import com.sparta.schedule_project.token.TokenProviderManager;
import jakarta.servlet.http.HttpServletRequest;
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
    private final TokenProviderManager tokenManager;
    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto createSchedule(HttpServletRequest req, CreateScheduleRequestDto requestDto) {
        String weather = weatherApi.getTodayWeather();
        User user = tokenManager.getTokenProvider(req).getUser(req);
        Schedule schedule = requestDto.convertDtoToEntity(user.getId(), weather);
        scheduleRepository.save(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE, req.getRequestURI());
    }

    /**
     * 일정을 조회
     *
     * @param req  HttpServletRequest 객체
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지당 항목 수 (기본값: 10)
     * @return 조회 결과 (ScheduleResponseDto)
     * @since 2024-10-03
     */
    public ScheduleResponseDto searchSchedule(HttpServletRequest req, int page, int size) {
        Page<Schedule> schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(page, size));
        return ScheduleResponseDto.createResponseDto(req.getRequestURI(), schedules);
    }

    /**
     * 일정 수정
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 수정할 일정 내용
     * @return 수정 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto updateSchedule(HttpServletRequest req, ModifyScheduleRequestDto requestDto) throws ResponseException {
        String weather = weatherApi.getTodayWeather();
        User user = tokenManager.getTokenProvider(req).getUser(req);
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId());
        validateAuth(user, schedule);
        schedule.update(requestDto, weather);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE, req.getRequestURI());
    }

    /**
     * 일정 삭제
     *
     * @param req        HttpServletRequest 객체
     * @param scheduleId 삭제할 일정 id
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto deleteSchedule(HttpServletRequest req, int scheduleId) throws ResponseException {
        User user = tokenManager.getTokenProvider(req).getUser(req);
        Schedule schedule = scheduleRepository.findById(scheduleId);
        validateAuth(user, schedule);
        scheduleRepository.delete(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE, req.getRequestURI());
    }

    /**
     * 일정 [수정/삭제] 요청에 대한 권한을 검사합니다.
     *
     * @param loginUser 로그인한 사용자 정보
     * @param schedule  [수정/삭제] 요청이 들어온 일정 정보
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void validateAuth(User loginUser, Schedule schedule) throws ResponseException {
        if (schedule == null)
            throw new ResponseException(ResponseCode.SCHEDULE_NOT_FOUND);
        if (loginUser.getAuth() != AuthType.ADMIN && loginUser.getId() != schedule.getUser().getId())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
