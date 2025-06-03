package com.xgaslan.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final String code;

    private final String level;

    public BaseException(String code, String level) {
        this.code = code;
        this.level = level;
    }

    public BaseException(String code, String level, Throwable cause) {
        super(null, cause);
        this.code = code;
        this.level = level;
    }

}
