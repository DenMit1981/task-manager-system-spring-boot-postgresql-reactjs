package com.training.denmit.taskManagerApi.exception;

public class UserIsPresentException extends RuntimeException {

    public UserIsPresentException(String msg) {
        super(msg);
    }
}
