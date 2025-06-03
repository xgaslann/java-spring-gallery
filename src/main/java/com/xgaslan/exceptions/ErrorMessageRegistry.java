package com.xgaslan.exceptions;

import java.util.Map;

public final class ErrorMessageRegistry {
    private static final Map<String, String> MESSAGES = Map.ofEntries(
            // SYSTEM errors
            Map.entry(ErrorCodes.System.SYSTEM_ERROR, "A system error has occurred."),
            Map.entry(ErrorCodes.System.INTERNAL_SERVER_ERROR, "A system error has occurred."),
            Map.entry(ErrorCodes.System.DATABASE_ERROR, "A database error has occurred."),
            Map.entry(ErrorCodes.System.CONFIGURATION_ERROR, "A configuration error has occurred."),
            Map.entry(ErrorCodes.System.UNAUTHORIZED_ERROR, "Unauthorized access."),
            Map.entry(ErrorCodes.System.FORBIDDEN_ERROR, "Access denied."),
            Map.entry(ErrorCodes.System.NOT_IMPLEMENTED_ERROR, "This feature is not implemented."),
            Map.entry(ErrorCodes.System.SESSION_MODEL_ERROR, "Session model error."),
            Map.entry(ErrorCodes.System.SESSION_NOT_FOUND_ERROR, "Session not found."),
            Map.entry(ErrorCodes.System.SESSION_EXPIRED_ERROR, "Session expired."),
            Map.entry(ErrorCodes.System.SESSION_INVALID_ERROR, "Invalid session."),
            Map.entry(ErrorCodes.System.SESSION_NOT_ACTIVE_ERROR, "Session is not active."),

            // BUSINESS.Common errors
            Map.entry(ErrorCodes.Business.Common.NOT_FOUND_ERROR, "Record not found."),
            Map.entry(ErrorCodes.Business.Common.NULL_ERROR, "A required value is missing."),
            Map.entry(ErrorCodes.Business.Common.FORBIDDEN_ERROR, "Access denied."),
            Map.entry(ErrorCodes.Business.Common.ALREADY_EXISTS_ERROR, "Record already exists."),
            Map.entry(ErrorCodes.Business.Common.UNAUTHORIZED_ERROR, "Unauthorized access."),
            Map.entry(ErrorCodes.Business.Common.INTERNAL_SERVER_ERROR, "A business error occurred."),
            Map.entry(ErrorCodes.Business.Common.VALIDATION_ERROR, "Validation error."),
            Map.entry(ErrorCodes.Business.Common.INVALID_REQUEST_ERROR, "Invalid request."),

            // BUSINESS.User errors
            Map.entry(ErrorCodes.Business.User.INVALID_EMAIL_OR_PASSWORD, "Invalid email or password."),
            Map.entry(ErrorCodes.Business.User.USER_NOT_FOUND, "User not found."),
            Map.entry(ErrorCodes.Business.User.USER_ALREADY_EXISTS, "User already exists."),
            Map.entry(ErrorCodes.Business.User.USER_FORBIDDEN, "User action is forbidden.")
    );

    public static String get(String errorCode) {
        return MESSAGES.getOrDefault(errorCode, "An unexpected error has occurred.");
    }

    private ErrorMessageRegistry() {}
}