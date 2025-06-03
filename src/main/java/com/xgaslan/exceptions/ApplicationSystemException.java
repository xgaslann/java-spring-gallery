package com.xgaslan.exceptions;

public class ApplicationSystemException extends BaseException {
    public ApplicationSystemException(String code) {
        super(code, ErrorLevels.SYSTEM);
    }

    public ApplicationSystemException(String code, Throwable cause) {
        super(code, ErrorLevels.SYSTEM, cause);
    }
}