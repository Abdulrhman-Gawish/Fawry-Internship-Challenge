package com.ecommerce.exception;

public class InsufficientBalanceException extends ECommerceException {
    public InsufficientBalanceException() {
        super("Insufficient balance");
    }
}