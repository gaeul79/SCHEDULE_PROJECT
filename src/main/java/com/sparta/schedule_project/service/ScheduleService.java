package com.sparta.schedule_project.service;

import com.sparta.schedule_project.common.CommonFunction;
import com.sparta.schedule_project.cookie.AuthType;
import com.sparta.schedule_project.dto.request.schedule.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.RemoveScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.SearchScheduleRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.schedule.ScheduleResponseDto;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.infra.WeatherApiService;
import com.sparta.schedule_project.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final WeatherApiService weatherApiService;
    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto createSchedule(HttpServletRequest req, CreateScheduleRequestDto requestDto) throws ResponseException {
        String weather = weatherApiService.getTodayWeather();
        User user = CommonFunction.getUserFromCookie(req);
        Schedule schedule = requestDto.convertDtoToEntity(requestDto, user.getSeq(), weather);
        scheduleRepository.save(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE);
    }

    /**
     * 일정을 조회
     *
     * @param requestDto 일정 조회 요청 정보
     * @return 조회 결과 (ScheduleResponseDto)
     * @since 2024-10-03
     */
    public ScheduleResponseDto searchSchedule(SearchScheduleRequestDto requestDto) {
        Schedule schedule = requestDto.convertDtoToEntity(requestDto);
        Page<Schedule> schedules = scheduleRepository.findAllByOrderByUpdateDateDesc(schedule.getPage());
        return ScheduleResponseDto.createResponseDto(schedules, ResponseCode.SUCCESS_SEARCH_SCHEDULE);
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
        String weather = weatherApiService.getTodayWeather();
        User user = CommonFunction.getUserFromCookie(req);
        Schedule schedule = scheduleRepository.findBySeq(requestDto.getScheduleSeq());
        checkAuth(user, schedule);
        schedule.update(requestDto, weather);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE);
    }

    /**
     * 일정 삭제
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 일정 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto deleteSchedule(HttpServletRequest req, RemoveScheduleRequestDto requestDto) throws ResponseException {
        User user = CommonFunction.getUserFromCookie(req);
        Schedule schedule = scheduleRepository.findBySeq(user.getSeq());
        checkAuth(user, schedule);
        scheduleRepository.delete(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE);
    }

    /**
     * 일정 [수정/삭제] 요청에 대한 권한을 검사합니다.
     *
     * @param loginUser 로그인한 사용자 정보
     * @param schedule  [수정/삭제] 요청이 들어온 일정 정보
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @since 2024-10-15
     */
    private void checkAuth(User loginUser, Schedule schedule) throws ResponseException {
        if (schedule == null)
            throw new ResponseException(ResponseCode.SCHEDULE_NOT_FOUND);
        else if (loginUser.getAuth() != AuthType.ADMIN &&
                 loginUser.getSeq() != schedule.getUser().getSeq())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
