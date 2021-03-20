package com.godel.simplewebapp.rest.exceptions;

import com.godel.simplewebapp.exceptions.EmployeeServiceException;
import com.godel.simplewebapp.exceptions.NotFoundEmployeeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        LOGGER.error("Malformed JSON request", ex);

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
        LOGGER.error("Not valid", ex);

        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorInfo errorInfo = new ErrorInfo(status, message, ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(errorInfo);
    }

    @ExceptionHandler(NotFoundEmployeeServiceException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundEmployeeServiceException(Exception ex) {
        LOGGER.error("Not found", ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, "Not found" ,ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

    @ExceptionHandler(EmployeeServiceException.class)
    public ResponseEntity<ErrorInfo> handleEmployeeServiceException(Exception ex){
        LOGGER.error(ex.getMessage(), ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getCause().getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorInfo);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleException(Exception ex){
        LOGGER.error("Server error", ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorInfo);
    }

}