package com.ecommerce.service.interfaces;

import com.ecommerce.model.cart.ShoppingCart;
import com.ecommerce.model.customer.Customer;

public interface CheckoutService {
    void checkout(Customer customer, ShoppingCart cart);
}