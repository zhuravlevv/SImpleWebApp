package com.godel.simplewebapp.rest.exceptions;

import com.godel.simplewebapp.service.exceptions.DateOfBirthException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(status, "Malformed JSON request", ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorInfo errorInfo = new ErrorInfo(status, message, ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(errorInfo);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorInfo> handleEmptyResultDataAccess(HttpServletRequest request, Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, "Such employee doesn't exist" ,ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }


    @ExceptionHandler(DateOfBirthException.class)
    public ResponseEntity<ErrorInfo> handleDateOfBirth(HttpServletRequest request, Exception ex){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST, "Change date of birth" ,ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorInfo);
    }

}