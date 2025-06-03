package com.xgaslan.exceptions;

import com.xgaslan.result.ErrorResult;
import com.xgaslan.result.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Environment environment;

    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ServiceResult<Object>> handleNotFound(
            ApplicationNotFoundException ex, HttpServletRequest req) {

        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();

        ServiceResult<Object> result = ServiceResult.failure(
                error,
                HttpStatus.NOT_FOUND.value()
        );
        log.warn("[{}] NotFoundException: code={} path={}", result.getTraceId(), ex.getCode(), req.getRequestURI());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler(ApplicationForbiddenException.class)
    public ResponseEntity<ServiceResult<Object>> handleForbidden(
            ApplicationForbiddenException ex, HttpServletRequest req) {

        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();

        ServiceResult<Object> result = ServiceResult.failure(
                error,
                HttpStatus.FORBIDDEN.value()
        );
        log.warn("[{}] ForbiddenException: code={} path={}", result.getTraceId(), ex.getCode(), req.getRequestURI());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler(ApplicationUnauthorizedException.class)
    public ResponseEntity<ServiceResult<Object>> handleUnauthorized(
            ApplicationUnauthorizedException ex, HttpServletRequest req) {

        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();

        ServiceResult<Object> result = ServiceResult.failure(
                error,
                HttpStatus.UNAUTHORIZED.value()
        );
        log.info("[{}] UnauthorizedException: code={} path={}", result.getTraceId(), ex.getCode(), req.getRequestURI());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ServiceResult<Object>> handleBusiness(
            ApplicationBusinessException ex, HttpServletRequest req) {

        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();

        ServiceResult<Object> result = ServiceResult.failure(
                error,
                HttpStatus.BAD_REQUEST.value()
        );
        log.warn("[{}] BusinessException: code={} path={}", result.getTraceId(), ex.getCode(), req.getRequestURI());
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResult<Object>> handleAny(
            Exception ex, HttpServletRequest req) {

        ErrorResult error = ErrorResult.builder()
                .code(ErrorCodes.System.INTERNAL_SERVER_ERROR)
                .message(ErrorMessageRegistry.get(ErrorCodes.System.INTERNAL_SERVER_ERROR))
                .detail(isProd() ? null : ex.toString())
                .build();

        ServiceResult<Object> result = ServiceResult.failure(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        log.error("[{}] UnknownException: path={} message={}", result.getTraceId(), req.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    private boolean isProd() {
        return environment != null && environment.matchesProfiles("prod");
    }
}