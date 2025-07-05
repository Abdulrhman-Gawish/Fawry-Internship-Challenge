package com.ecommerce.exception;

public class ExpiredProductException extends ECommerceException {
    public ExpiredProductException(String productName) {
        super("Product expired: " + productName);
    }
}