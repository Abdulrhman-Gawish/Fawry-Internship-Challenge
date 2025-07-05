package com.ecommerce.model.product;

import com.ecommerce.interfaces.Shippable;
import com.ecommerce.interfaces.Expirable;

import java.time.LocalDate;

public class PerishableProduct extends Product implements Expirable, Shippable {
    private LocalDate expirationDate;
    private double weight;

    public PerishableProduct(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    protected boolean isExpiredProduct() {
        return isExpired();
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
