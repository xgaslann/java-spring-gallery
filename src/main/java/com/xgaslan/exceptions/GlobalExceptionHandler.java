package com.xgaslan.exceptions;

import com.xgaslan.result.ErrorResult;
import com.xgaslan.result.ServiceResult;
import com.xgaslan.util.TraceIdFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Environment environment;

    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    private boolean isProd() {
        return environment != null && environment.matchesProfiles("prod");
    }

    // Common error return function
    private ResponseEntity<ServiceResult<Object>> buildError(
            ErrorResult error, int status, HttpServletRequest req, Exception ex
    ) {
        String traceId = (String) req.getAttribute(TraceIdFilter.TRACE_ID);
        if (status >= 500) {
            log.error("[{}] {}: path={} message={}", traceId, ex.getClass().getSimpleName(), req.getRequestURI(), ex.getMessage(), ex);
        } else {
            log.warn("[{}] {}: path={} message={}", traceId, ex.getClass().getSimpleName(), req.getRequestURI(), ex.getMessage());
        }
        return ResponseEntity.status(status).body(ServiceResult.failure(error, status));
    }

    // ---- Custom Application Exceptions ----

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ServiceResult<Object>> handleNotFound(ApplicationNotFoundException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.NOT_FOUND.value(), req, ex);
    }

    @ExceptionHandler(ApplicationForbiddenException.class)
    public ResponseEntity<ServiceResult<Object>> handleForbidden(ApplicationForbiddenException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.FORBIDDEN.value(), req, ex);
    }

    @ExceptionHandler(ApplicationUnauthorizedException.class)
    public ResponseEntity<ServiceResult<Object>> handleUnauthorized(ApplicationUnauthorizedException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNAUTHORIZED.value(), req, ex);
    }

    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ServiceResult<Object>> handleBusiness(ApplicationBusinessException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code(ex.getCode())
                .message(ErrorMessageRegistry.get(ex.getCode()))
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.BAD_REQUEST.value(), req, ex);
    }

    // ---- Standard Framework And Validation Exceptions ----

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ServiceResult<Object>> handleNotFound(Exception ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("NotFound")
                .message("Resource not found")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.NOT_FOUND.value(), req, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceResult<Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
        ErrorResult error = ErrorResult.builder()
                .code("Validation.Error")
                .message("Validation failed for request")
                .detail(detail)
                .build();
        return buildError(error, HttpStatus.BAD_REQUEST.value(), req, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ServiceResult<Object>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        String detail = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Constraint violation");
        ErrorResult error = ErrorResult.builder()
                .code("Validation.ConstraintViolation")
                .message("Validation error occurred")
                .detail(detail)
                .build();
        return buildError(error, HttpStatus.BAD_REQUEST.value(), req, ex);
    }

    // ---- JWT & Security Exceptions ----

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ServiceResult<Object>> handleExpiredJwt(ExpiredJwtException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("JWT.TokenExpired")
                .message("JWT token is expired")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNAUTHORIZED.value(), req, ex);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ServiceResult<Object>> handleSignature(SignatureException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("JWT.InvalidSignature")
                .message("JWT token signature is invalid")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNAUTHORIZED.value(), req, ex);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ServiceResult<Object>> handleJwt(JwtException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("JWT.Invalid")
                .message("JWT token is invalid")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNAUTHORIZED.value(), req, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ServiceResult<Object>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Security.AccessDenied")
                .message("Access is denied")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.FORBIDDEN.value(), req, ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ServiceResult<Object>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("User.InvalidCredentials")
                .message("Invalid username or password")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNAUTHORIZED.value(), req, ex);
    }

    // ---- HTTP ve JSON Exceptions ----

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ServiceResult<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Http.MethodNotSupported")
                .message("HTTP method not supported")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.METHOD_NOT_ALLOWED.value(), req, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ServiceResult<Object>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Http.MediaTypeNotSupported")
                .message("Content type not supported")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), req, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServiceResult<Object>> handleMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Http.MessageNotReadable")
                .message("Malformed JSON or request body")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.BAD_REQUEST.value(), req, ex);
    }

    // ---- JPA/Data Exceptions ----

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ServiceResult<Object>> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Database.ConstraintViolation")
                .message("A database constraint was violated")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.CONFLICT.value(), req, ex);
    }

    // ---- Java Standard Exceptions ----

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ServiceResult<Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("System.IllegalArgument")
                .message("Invalid argument")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.BAD_REQUEST.value(), req, ex);
    }

    // ---- Entity Not Found (Custom) ----

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String entity, Object id) {
            super(entity + " with id " + id + " not found.");
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ServiceResult<Object>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code("Entity.NotFound")
                .message("Resource not found")
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.NOT_FOUND.value(), req, ex);
    }

    // ---- Fallback (General) Exception Handler ----

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResult<Object>> handleAny(Exception ex, HttpServletRequest req) {
        ErrorResult error = ErrorResult.builder()
                .code(ErrorCodes.System.INTERNAL_SERVER_ERROR)
                .message(ErrorMessageRegistry.get(ErrorCodes.System.INTERNAL_SERVER_ERROR))
                .detail(isProd() ? null : ex.getMessage())
                .build();
        return buildError(error, HttpStatus.INTERNAL_SERVER_ERROR.value(), req, ex);
    }
}