package com.xgaslan.exceptions;

public class ApplicationBusinessException extends BaseException {
    public ApplicationBusinessException(String code) {
        super(code, ErrorLevels.BUSINESS);
    }

    public ApplicationBusinessException(String code, Throwable cause) {
        super(code, ErrorLevels.BUSINESS, cause);
    }
}