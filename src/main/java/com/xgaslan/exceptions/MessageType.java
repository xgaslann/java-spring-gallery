package com.xgaslan.exceptions;

import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXISTS("1004", "No record exists with the given ID"),
    GENERAL_EXCEPTION("9999", "An unexpected error occurred"),;

    private final String code;

    private final String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
