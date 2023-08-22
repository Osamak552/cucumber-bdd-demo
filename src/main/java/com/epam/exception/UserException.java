package com.epam.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends Exception{

    private final String message;
    private final String exceptionDetails;

    public UserException(String message, String exceptionDetails) {
        this.message = message;
        this.exceptionDetails = exceptionDetails;
    }


}
