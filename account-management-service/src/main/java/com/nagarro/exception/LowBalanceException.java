package com.nagarro.exception;

public class LowBalanceException extends RuntimeException{

    public LowBalanceException(String message) {
        super(message);
    }
}