package com.ecommerce;

import com.ecommerce.model.cart.ShoppingCart;
import com.ecommerce.model.customer.Customer;
import com.ecommerce.model.product.DigitalProduct;
import com.ecommerce.model.product.ElectronicProduct;
import com.ecommerce.model.product.PerishableProduct;
import com.ecommerce.service.impl.CheckoutServiceImpl;
import com.ecommerce.service.impl.ShippingServiceImpl;
import com.ecommerce.service.interfaces.CheckoutService;
import com.ecommerce.service.interfaces.ShippingService;

import java.time.LocalDate;

public class ECommerceSystem {
    public static void main(String[] args) {
        ShippingService shippingService = new ShippingServiceImpl();
        CheckoutService checkoutService = new CheckoutServiceImpl(shippingService);

        // Create products
        PerishableProduct cheese = new PerishableProduct("Cheese", 100, 10,
                LocalDate.now().plusDays(7), 0.2);
        PerishableProduct biscuits = new PerishableProduct("Biscuits", 150, 5,
                LocalDate.now().plusDays(30), 0.7);
        ElectronicProduct tv = new ElectronicProduct("TV", 500, 3, 15.0);
        ElectronicProduct mobile = new ElectronicProduct("Mobile", 300, 8, 0.5);
        DigitalProduct scratchCard = new DigitalProduct("Mobile Scratch Card", 50, 20);

        Customer customer = new Customer("Malak", 1000);

        ShoppingCart cart = new ShoppingCart();

        System.out.println("=== E-Commerce System Demo ===\n");

        // Case 1: Successful checkout with mixed products
        System.out.println("Example 1: Mixed products checkout");
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(scratchCard, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Case 2: Checkout with TV
        System.out.println("Example 2: Heavy item checkout");
        try {
            cart.add(tv, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Case 3: Empty cart error
        System.out.println("Example 3: Empty cart error");
        try {
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Case 4: Insufficient balance
        System.out.println("Example 4: Insufficient balance");
        try {
            cart.add(mobile, 5);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Case 5: Out of stock
        System.out.println("Example 5: Out of stock error");
        try {
            cart.clear();
            cart.add(tv, 5);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Case 6: Expired product
        System.out.println("Example 6: Expired product");
        PerishableProduct expiredMilk = new PerishableProduct("Expired Milk", 80, 5,
                LocalDate.now().minusDays(1), 1.0);
        try {
            cart.clear();
            cart.add(expiredMilk, 1);
            checkoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.printf("\nFinal customer balance: %.0f%n", customer.getBalance());
    }
}

