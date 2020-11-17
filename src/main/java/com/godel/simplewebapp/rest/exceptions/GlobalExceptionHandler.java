package com.godel.simplewebapp.rest.exceptions;

import com.godel.simplewebapp.exceptions.EmployeeServiceException;
import com.godel.simplewebapp.exceptions.NotFoundEmployeeServiceException;
import org.hibernate.exception.JDBCConnectionException;
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
        LOGGER.error(String.valueOf(ex), ex);

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
        LOGGER.error(String.valueOf(ex), ex);

        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorInfo errorInfo = new ErrorInfo(status, message, ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(errorInfo);
    }

    @ExceptionHandler(NotFoundEmployeeServiceException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundEmployeeServiceException(Exception ex) {
        LOGGER.error(String.valueOf(ex), ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, "Not found" ,ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

    @ExceptionHandler(EmployeeServiceException.class)
    public ResponseEntity<ErrorInfo> handleEmployeeServiceException(Exception ex){
        LOGGER.error(String.valueOf(ex), ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getCause().getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<ErrorInfo> handleJDBCConnectionException(Exception ex){
        LOGGER.error(String.valueOf(ex), ex);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't connect to database", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorInfo);
    }

}