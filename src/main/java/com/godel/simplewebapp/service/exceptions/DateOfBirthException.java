package com.godel.simplewebapp.service.exceptions;

public class DateOfBirthException extends RuntimeException {

    public DateOfBirthException(String date){
        super("Date of birth '" + date + "' doesn't exist. Employee can't be born in the future.");
    }
}
