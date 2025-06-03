package com.xgaslan.exceptions;

public class ApplicationNotFoundException extends ApplicationBusinessException {
    public ApplicationNotFoundException() {
        super(ErrorCodes.Business.Common.NOT_FOUND_ERROR);
    }
}