package com.ecommerce.service.interfaces;

import com.ecommerce.interfaces.Shippable;
import com.ecommerce.model.product.Product;

import java.util.List;
import java.util.Map;

public interface ShippingService {
    double calculateShippingFee(List<Shippable> items);
    void processShipment(List<Shippable> items, Map<Product, Integer> quantities);
}