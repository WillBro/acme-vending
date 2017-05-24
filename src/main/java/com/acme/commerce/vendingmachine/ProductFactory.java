package com.acme.commerce.vendingmachine;

import com.acme.commerce.vendingmachine.impl.ProductImpl;

/**
 * Product Factory
 *
 * @author William Brown
 * @since 1.0
 */
public class ProductFactory {
    public static Product createProduct(String name, int cost, int quantity) {
        return new ProductImpl(name, cost, quantity);
    }
}
