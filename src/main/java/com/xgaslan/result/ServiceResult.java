package com.xgaslan.result;

import com.xgaslan.exceptions.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {
    private boolean isSuccess;
    private T result;
    private ErrorResult errorResult;

    public static <T> ServiceResult<T> ok(T data) {
        ServiceResult<T> r = new ServiceResult<>();
        r.isSuccess = true;
        r.result = data;
        return r;
    }
    public static <T> ServiceResult<T> created(T data) {
        ServiceResult<T> r = new ServiceResult<>();
        r.isSuccess = true;
        r.result = data;
        return r;
    }
    public static <T> ServiceResult<T> accepted(T data) {
        ServiceResult<T> r = new ServiceResult<>();
        r.isSuccess = true;
        r.result = data;
        return r;
    }
    public static <T> ServiceResult<T> noContent() {
        ServiceResult<T> r = new ServiceResult<>();
        r.isSuccess = true;
        r.result = null;
        return r;
    }
    public static <T> ServiceResult<T> empty() {
        return noContent();
    }

    public static <T> ServiceResult<T> error(ErrorResult error) {
        ServiceResult<T> r = new ServiceResult<>();
        r.isSuccess = false;
        r.errorResult = error;
        return r;
    }

    public static <T> ServiceResult<T> badRequest(String message) {
        ErrorModel error = ErrorModel.of(ErrorCodes.Business.Common.VALIDATION_ERROR, message, null);
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.value(), error);
        return error(errorResult);
    }

    public static <T> ServiceResult<T> notFound(String message) {
        ErrorModel error = ErrorModel.of(ErrorCodes.Business.Common.NOT_FOUND_ERROR, message, null);
        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND.value(), error);
        return error(errorResult);
    }

    public static <T> ServiceResult<T> forbidden(String message) {
        ErrorModel error = ErrorModel.of(ErrorCodes.Business.Common.FORBIDDEN_ERROR, message, null);
        ErrorResult errorResult = new ErrorResult(HttpStatus.FORBIDDEN.value(), error);
        return error(errorResult);
    }

    public static <T> ServiceResult<T> unauthorized(String message) {
        ErrorModel error = ErrorModel.of(ErrorCodes.System.UNAUTHORIZED_ERROR, message, null);
        ErrorResult errorResult = new ErrorResult(HttpStatus.UNAUTHORIZED.value(), error);
        return error(errorResult);
    }

    public static <T> ServiceResult<T> alreadyExists(String message) {
        ErrorModel error = ErrorModel.of(ErrorCodes.Business.Common.ALREADY_EXISTS_ERROR, message, null);
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.value(), error);
        return error(errorResult);
    }

    public static <T> ServiceResult<T> systemError(String message, Exception ex) {
        ErrorModel error = ErrorModel.of(ErrorCodes.System.INTERNAL_SERVER_ERROR, message, ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
        return error(errorResult);
    }
}
