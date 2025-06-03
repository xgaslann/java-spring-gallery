package com.xgaslan.exceptions;

public class ApplicationForbiddenException extends ApplicationBusinessException {
    public ApplicationForbiddenException() {
        super(ErrorCodes.Business.Common.FORBIDDEN_ERROR);
    }
}