package com.godel.simplewebapp.exceptions;

public class NotFoundEmployeeServiceException extends RuntimeException{

    public NotFoundEmployeeServiceException(String message) {
        super(message);
    }

}
