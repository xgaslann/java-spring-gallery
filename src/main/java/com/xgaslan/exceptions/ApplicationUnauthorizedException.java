package com.xgaslan.exceptions;

public class ApplicationUnauthorizedException extends ApplicationSystemException {
    public ApplicationUnauthorizedException() {
        super(ErrorCodes.System.UNAUTHORIZED_ERROR);
    }
}
