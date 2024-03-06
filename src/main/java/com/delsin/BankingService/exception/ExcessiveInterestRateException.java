package com.delsin.BankingService.exception;

public class ExcessiveInterestRateException extends RuntimeException {
    public ExcessiveInterestRateException() {
        super("Deposit interest exceeded 207%");
    }

    public ExcessiveInterestRateException(String message) {
        super(message);
    }
}