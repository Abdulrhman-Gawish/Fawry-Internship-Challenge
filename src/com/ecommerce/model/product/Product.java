package com.ecommerce.model.product;

public abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }

    /**
     * methods to check product availability
     */
    public boolean isAvailable(int requestedQuantity) {
        return hasStock(requestedQuantity) && !isExpiredProduct();
    }

    private boolean hasStock(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }

    /**
     * methods - to be overridden by expirable products
     */
    protected boolean isExpiredProduct() {
        return false;
    }

    public void reduceQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        }
    }


}
