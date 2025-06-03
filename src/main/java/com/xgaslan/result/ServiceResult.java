package com.xgaslan.result;

import com.xgaslan.exceptions.ErrorCodes;
import com.xgaslan.exceptions.ErrorMessageRegistry;
import lombok.Getter;

import lombok.Builder;

@Getter
@Builder
public class ServiceResult<T> {
    private final boolean success;
    private final int status;
    private final String traceId;
    private final String timestamp;
    private final T data;
    private final ErrorResult error;

    // ==== SUCCESS FACTORY METHODS ====
    public static <T> ServiceResult<T> success(T data, int status) {
        return ServiceResult.<T>builder()
                .success(true)
                .status(status)
                .traceId(generateTraceId())
                .timestamp(now())
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ServiceResult<T> success(T data) {
        return success(data, 200);
    }

    public static <T> ServiceResult<T> created(T data) {
        return success(data, 201);
    }

    public static <T> ServiceResult<T> accepted(T data) {
        return success(data, 202);
    }

    public static <T> ServiceResult<T> noContent() {
        return success(null, 204);
    }

    // ==== ERROR/FAILURE FACTORY METHODS ====

    // Ana failure factory (en detaylı hali)
    public static <T> ServiceResult<T> failure(String code, String message, String detail, int status) {
        return ServiceResult.<T>builder()
                .success(false)
                .status(status)
                .traceId(generateTraceId())
                .timestamp(now())
                .data(null)
                .error(ErrorResult.builder()
                        .code(code)
                        .message(message)
                        .detail(detail)
                        .build())
                .build();
    }

    // Kısa versiyonlar (overload’lar)
    public static <T> ServiceResult<T> failure(String code, String message, int status) {
        return failure(code, message, null, status);
    }

    public static <T> ServiceResult<T> failure(String code, int status) {
        return failure(code, ErrorMessageRegistry.get(code), null, status);
    }

    public static <T> ServiceResult<T> failure(ErrorResult error, int status) {
        return ServiceResult.<T>builder()
                .success(false)
                .status(status)
                .traceId(generateTraceId())
                .timestamp(now())
                .data(null)
                .error(error)
                .build();
    }

    // Sık kullanılanlar için shortcut factory’ler:
    public static <T> ServiceResult<T> notFound(String detail) {
        return failure(ErrorCodes.Business.Common.NOT_FOUND_ERROR, "Record not found.", detail, 404);
    }

    public static <T> ServiceResult<T> badRequest(String detail) {
        return failure(ErrorCodes.Business.Common.VALIDATION_ERROR, "Validation error.", detail, 400);
    }

    public static <T> ServiceResult<T> forbidden(String detail) {
        return failure(ErrorCodes.Business.Common.FORBIDDEN_ERROR, "Forbidden.", detail, 403);
    }

    public static <T> ServiceResult<T> unauthorized(String detail) {
        return failure(ErrorCodes.System.UNAUTHORIZED_ERROR, "Unauthorized.", detail, 401);
    }

    public static <T> ServiceResult<T> alreadyExists(String detail) {
        return failure(ErrorCodes.Business.Common.ALREADY_EXISTS_ERROR, "Already exists.", detail, 400);
    }

    public static <T> ServiceResult<T> systemError(String detail) {
        return failure(ErrorCodes.System.INTERNAL_SERVER_ERROR, "Internal server error.", detail, 500);
    }

    // ==== UTILITY ====
    private static String now() {
        return java.time.OffsetDateTime.now().toString();
    }

    private static String generateTraceId() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}