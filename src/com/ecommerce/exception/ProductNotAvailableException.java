package com.ecommerce.exception;

public class ProductNotAvailableException extends ECommerceException {
    public ProductNotAvailableException(String productName) {
        super("Product not available: " + productName);
    }
}