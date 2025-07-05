package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.interfaces.Expirable;
import com.ecommerce.interfaces.Shippable;
import com.ecommerce.model.cart.CartItem;
import com.ecommerce.model.cart.ShoppingCart;
import com.ecommerce.model.customer.Customer;
import com.ecommerce.model.product.Product;
import com.ecommerce.service.interfaces.CheckoutService;
import com.ecommerce.service.interfaces.ShippingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutServiceImpl implements CheckoutService {
    private final ShippingService shippingService;

    public CheckoutServiceImpl(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Override
    public void checkout(Customer customer, ShoppingCart cart) {
        try {
            validateCheckout(customer, cart);

            List<Shippable> shippableItems = new ArrayList<>();
            Map<Product, Integer> quantities = new HashMap<>();
            double subtotal = 0;

            for (CartItem item : cart.getItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();

                if (!product.isAvailable(quantity)) {
                    throw new ProductNotAvailableException(product.getName());
                }

                subtotal += item.getTotalPrice();

                if (product instanceof Shippable) {
                    shippableItems.add((Shippable) product);
                    quantities.put(product, quantity);
                }

                product.reduceQuantity(quantity);
            }

            double shippingFee = shippingService.calculateShippingFee(shippableItems);
            double totalAmount = subtotal + shippingFee;

            if (!customer.hasEnoughBalance(totalAmount)) {
                throw new InsufficientBalanceException();
            }

            customer.deductBalance(totalAmount);

            shippingService.processShipment(shippableItems, quantities);

            printReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());

            cart.clear();

        } catch (ECommerceException e) {
            System.err.println("Checkout failed: " + e.getMessage());
            throw e;
        }
    }

    private void validateCheckout(Customer customer, ShoppingCart cart) {
        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        // Check if any products are expired or out of stock
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (!product.isAvailable(item.getQuantity())) {
                if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                    throw new ExpiredProductException(product.getName());
                } else {
                    throw new ProductNotAvailableException(product.getName());
                }
            }
        }
    }

    private void printReceipt(ShoppingCart cart, double subtotal,
                              double shippingFee, double totalAmount,
                              double remainingBalance) {
        System.out.println("** Checkout receipt **");

        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice());
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
        System.out.printf("Customer balance after payment: %.0f%n", remainingBalance);
        System.out.println();
    }
}