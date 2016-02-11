package com.github.kazuki43zoo.sample.api.component.error;

import com.github.kazuki43zoo.sample.domain.service.TodoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Autowired
    ApiErrorCreator apiErrorCreator;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = apiErrorCreator.createByException(ex, ex.getMessage(), request.getLocale());
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = apiErrorCreator.createByBindingResult(ex.getBindingResult(), ex, ex.getMessage(), request.getLocale());
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = apiErrorCreator.createByBindingResult(ex.getBindingResult(), ex, ex.getMessage(), request.getLocale());
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleTodoNotFoundException(
            TodoNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSystemException(
            Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiError apiError = apiErrorCreator.createByException(ex, "System error is occurred", request.getLocale());
        return super.handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}