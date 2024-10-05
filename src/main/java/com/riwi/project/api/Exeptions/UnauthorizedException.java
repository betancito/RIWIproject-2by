package com.riwi.project.api.Exeptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
