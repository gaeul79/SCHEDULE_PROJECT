//package com.sparta.schedule_project.dto.response.modify;
//
//import com.sparta.schedule_project.dto.response.ResponseStatusDto;
//import com.sparta.schedule_project.exception.ResponseCode;
//
//import java.util.List;
//
///**
// * 서버로부터 일정 정보를 받을 때 사용하는 DTO 클래스
// *
// * @author 김현정
// * @since 2024-10-03
// */
//public class ModifyScheduleResponseDto {
//    private List<ScheduleView> schedule;
//    private ResponseStatusDto responseStatusDto;
//    private int totalCount;
//
//    public List<ScheduleView> getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(List<ScheduleView> schedule) {
//        this.schedule = schedule;
//    }
//
//    public ResponseStatusDto getResponseStatusDto() {
//        return responseStatusDto;
//    }
//
//    public void setResponseStatusDto(ResponseStatusDto responseStatusDto) {
//        this.responseStatusDto = responseStatusDto;
//    }
//
//    public void setResponseStatusDto(ResponseCode responseCode) {
//        this.responseStatusDto = new ResponseStatusDto(responseCode);
//    }
//
//    public int getTotalCount() {
//        return totalCount;
//    }
//
//    public void setTotalCount(int totalCount) {
//        this.totalCount = totalCount;
//    }
//
//    public static ModifyScheduleResponseDto from(List<ScheduleView> schedules, ResponseCode responseCode) {
//        ModifyScheduleResponseDto responseScheduleDto = new ModifyScheduleResponseDto();
//        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
//        responseScheduleDto.setSchedule(schedules);
//        responseScheduleDto.setResponseStatusDto(responseStatusDto);
//        return responseScheduleDto;
//    }
//
//    public static ModifyScheduleResponseDto from(List<ScheduleView> schedules, ResponseCode responseCode, String message) {
//        ModifyScheduleResponseDto responseScheduleDto = new ModifyScheduleResponseDto();
//        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, message);
//        responseScheduleDto.setSchedule(schedules);
//        responseScheduleDto.setResponseStatusDto(responseStatusDto);
//        return responseScheduleDto;
//    }
//}
