package com.godel.simplewebapp.rest.exceptions;

import com.godel.simplewebapp.exceptions.EmployeeServiceException;
import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(status, "Malformed JSON request", ex.getMessage());
        LOGGER.error(String.valueOf(errorInfo));
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
        LOGGER.error(String.valueOf(errorInfo));
        return ResponseEntity
                .status(status)
                .body(errorInfo);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorInfo> handleEmptyResultDataAccess(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, "Such employee doesn't exist" ,ex.getMessage());
        LOGGER.error(String.valueOf(errorInfo));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

    @ExceptionHandler(EmployeeServiceException.class)
    public ResponseEntity<ErrorInfo> handleEmployeeServiceException(Exception ex){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, ex.getCause().getMessage(), ex.getMessage());
        LOGGER.error(String.valueOf(errorInfo));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

}