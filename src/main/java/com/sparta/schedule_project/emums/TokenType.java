package com.sparta.schedule_project.emums;

public enum TokenType {
    COOKIE,
    HEADER;

    public boolean isTypeCookie(TokenType tokenType) {
        return switch (tokenType) {
            case COOKIE -> true;
            case HEADER -> false;
        };
    }
}
