package com.ecommerce.model.cart;

import com.ecommerce.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (!product.isAvailable(quantity)) {
            throw new IllegalStateException("Product not available: " + product.getName());
        }

        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                CartItem newItem = new CartItem(product, item.getQuantity() + quantity);
                items.remove(item);
                items.add(newItem);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
