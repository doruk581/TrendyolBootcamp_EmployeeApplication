package com.trendyol.bootcampemployeemanagement.application;

import com.trendyol.bootcampemployeemanagement.domain.exception.EmployeeNotFoundException;
import com.trendyol.bootcampemployeemanagement.domain.exception.NotAuthorizedException;
import com.trendyol.bootcampemployeemanagement.interfaces.ApiError;
import com.trendyol.bootcampemployeemanagement.interfaces.Error;
import com.trendyol.bootcampemployeemanagement.interfaces.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Özgü bir exception tipi için handler
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomException(EmployeeNotFoundException e) {
        final Error error = new Error();
        error.setErrorCode(ErrorCode.EMPLOYEE_NOT_FOUND.code());
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        final ApiError apiError = new ApiError(error);

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException tipi için handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Genel bir exception tipi için handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
