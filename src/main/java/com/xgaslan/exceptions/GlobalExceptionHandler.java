package com.xgaslan.exceptions;

import com.xgaslan.result.ErrorModel;
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

    // 404 Not Found
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ServiceResult<Object>> handleNotFound(
            ApplicationNotFoundException ex, HttpServletRequest req) {

        ErrorModel error = ErrorModel.of(
                ex.getCode(),
                ErrorMessageRegistry.get(ex.getCode()),
                isProd() ? null : ex.getMessage()
        );
        log.warn("[{}] NotFoundException: code={} path={}", error.getTraceId(), ex.getCode(), req.getRequestURI());

        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND.value(), error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceResult.error(errorResult));
    }

    // 403 Forbidden
    @ExceptionHandler(ApplicationForbiddenException.class)
    public ResponseEntity<ServiceResult<Object>> handleForbidden(
            ApplicationForbiddenException ex, HttpServletRequest req) {

        ErrorModel error = ErrorModel.of(
                ex.getCode(),
                ErrorMessageRegistry.get(ex.getCode()),
                isProd() ? null : ex.getMessage()
        );
        log.warn("[{}] ForbiddenException: code={} path={}", error.getTraceId(), ex.getCode(), req.getRequestURI());

        ErrorResult errorResult = new ErrorResult(HttpStatus.FORBIDDEN.value(), error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ServiceResult.error(errorResult));
    }

    // 401 Unauthorized
    @ExceptionHandler(ApplicationUnauthorizedException.class)
    public ResponseEntity<ServiceResult<Object>> handleUnauthorized(
            ApplicationUnauthorizedException ex, HttpServletRequest req) {

        ErrorModel error = ErrorModel.of(
                ex.getCode(),
                ErrorMessageRegistry.get(ex.getCode()),
                isProd() ? null : ex.getMessage()
        );
        log.info("[{}] UnauthorizedException: code={} path={}", error.getTraceId(), ex.getCode(), req.getRequestURI());

        ErrorResult errorResult = new ErrorResult(HttpStatus.UNAUTHORIZED.value(), error);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ServiceResult.error(errorResult));
    }

    // 400 BadRequest & Business Exception
    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ServiceResult<Object>> handleBusiness(
            ApplicationBusinessException ex, HttpServletRequest req) {

        ErrorModel error = ErrorModel.of(
                ex.getCode(),
                ErrorMessageRegistry.get(ex.getCode()),
                isProd() ? null : ex.getMessage()
        );
        log.warn("[{}] BusinessException: code={} path={}", error.getTraceId(), ex.getCode(), req.getRequestURI());

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.value(), error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServiceResult.error(errorResult));
    }

    // 500 Internal Server Error (all other exceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResult<Object>> handleAny(
            Exception ex, HttpServletRequest req) {

        ErrorModel error = ErrorModel.of(
                ErrorCodes.System.INTERNAL_SERVER_ERROR,
                ErrorMessageRegistry.get(ErrorCodes.System.INTERNAL_SERVER_ERROR),
                isProd() ? null : ex.toString()
        );
        log.error("[{}] UnknownException: path={} message={}", error.getTraceId(), req.getRequestURI(), ex.getMessage(), ex);

        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResult.error(errorResult));
    }

    // Checks if the active Spring profile is production
    private boolean isProd() {
        return environment != null && environment.matchesProfiles("prod");
    }
}