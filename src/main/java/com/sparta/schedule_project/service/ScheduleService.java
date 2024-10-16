package com.sparta.schedule_project.service;

import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.jwt.AuthType;
import com.sparta.schedule_project.jwt.CookieManager;
import com.sparta.schedule_project.infra.WeatherApiService;
import com.sparta.schedule_project.dto.request.schedule.CreateScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.ModifyScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.RemoveScheduleRequestDto;
import com.sparta.schedule_project.dto.request.schedule.SearchScheduleRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.schedule.ScheduleResponseDto;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.ScheduleRepository;
import com.sparta.schedule_project.repository.UserRepository;
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
    private final CookieManager cookieManager;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성
     *
     * @param requestDto 일정 등록 요청 정보
     * @return 생성 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto createSchedule(String token, CreateScheduleRequestDto requestDto) throws ResponseException {
        String weather = weatherApiService.getTodayWeather();
        User user = getLoginUserInfo(token);
        Schedule schedule = requestDto.convertDtoToEntity(requestDto, user.getSeq(), weather);
        scheduleRepository.save(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_SCHEDULE);
    }

    /**
     * 일정을 조회
     *
     * @param requestDto 일정 조회 요청 정보
     * @return 조회 결과 (ScheduleResponseDto)
     * @author 김현정
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
     * @param requestDto 일정 수정 요청 정보
     * @return 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto updateSchedule(String token, ModifyScheduleRequestDto requestDto) throws ResponseException {
        String weather = weatherApiService.getTodayWeather();
        Schedule schedule = requestDto.convertDtoToEntity(requestDto, weather);
        Schedule updateSchedule = scheduleRepository.findBySeq(schedule.getUser().getSeq());
        checkValue(token, updateSchedule);
        updateSchedule.update(schedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_SCHEDULE);
    }

    /**
     * 일정 삭제
     *
     * @param requestDto 일정 삭제 요청 정보
     * @return 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto deleteSchedule(String token, RemoveScheduleRequestDto requestDto) throws ResponseException {
        Schedule schedule = requestDto.convertDtoToEntity(requestDto);
        Schedule deleteSchedule = scheduleRepository.findBySeq(schedule.getUser().getSeq());
        checkValue(token, deleteSchedule);
        scheduleRepository.delete(deleteSchedule);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_SCHEDULE);
    }

    /**
     * 일정 수정 요청에 대한 권한을 검사합니다.
     *
     * @throws ResponseException 권한이 없는 경우 예외를 발생시킵니다.
     * @author 김현정
     * @since 2024-10-04
     */
    private void checkValue(String token, Schedule schedule) throws ResponseException {
        User loginUser = getLoginUserInfo(token);
        if(schedule == null)
            throw new ResponseException(ResponseCode.SCHEDULE_NOT_FOUND);
        else if(loginUser.getAuth() != AuthType.ADMIN ||
                loginUser.getSeq() != schedule.getUser().getSeq())
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    private User getLoginUserInfo(String token) throws ResponseException {
        User loginUser = cookieManager.getUserFromJwtToken(token);
        User searchUser = userRepository.findByEmail(loginUser.getEmail());
        if(searchUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);

        if (!loginUser.getEmail().equals(searchUser.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        return loginUser;
    }
}
