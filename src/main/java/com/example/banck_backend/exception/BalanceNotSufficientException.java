package com.example.banck_backend.exception;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String message){
        super(message);

}
}