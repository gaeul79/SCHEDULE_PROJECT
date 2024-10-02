package com.sparta.schedule_project.dto;

import lombok.Data;

@Data
public class StatusDto {
    private int state;
    private String message;

    public StatusDto() {

    }

    public StatusDto(int state, String message) {
        this.state = state;
        this.message = message;
    }
}
