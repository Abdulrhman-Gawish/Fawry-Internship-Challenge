package com.ecommerce.exception;

public class EmptyCartException extends ECommerceException {
    public EmptyCartException() {
        super("Cart is empty");
    }
}
