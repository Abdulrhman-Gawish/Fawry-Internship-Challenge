package com.ecommerce.service.impl;


import com.ecommerce.interfaces.Shippable;
import com.ecommerce.model.product.Product;
import com.ecommerce.service.interfaces.ShippingService;

import java.util.List;
import java.util.Map;

public class ShippingServiceImpl implements ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 10.0; // assumed value

    @Override
    public double calculateShippingFee(List<Shippable> items) {
        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        return totalWeight * SHIPPING_RATE_PER_KG;
    }

    @Override
    public void processShipment(List<Shippable> items, Map<Product, Integer> quantities) {
        if (items.isEmpty()) return;

        System.out.println("** Shipment notice **");
        double totalWeight = 0;

        for (Shippable item : items) {
            int qty = quantities.getOrDefault(item, 1);
            double itemWeight = item.getWeight() * qty;
            totalWeight += itemWeight;

            System.out.printf("%dx %s %.0fg%n", qty, item.getName(), itemWeight * 1000);
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        System.out.println();
    }
}