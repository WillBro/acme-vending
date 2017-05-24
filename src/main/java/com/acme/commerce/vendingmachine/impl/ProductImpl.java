package com.acme.commerce.vendingmachine.impl;

import com.acme.commerce.vendingmachine.Product;

/**
 * Product Implementation
 *
 * @author William Brown
 * @since 1.0
 */
public class ProductImpl implements Product {
    private String name;
    private int cost;
    private int quantityAvailable;

    public ProductImpl(String name, int cost, int quantityAvailable) {
        this.name = name;
        this.cost = cost;
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getQuantityAvailable() {
        return this.quantityAvailable;
    }

    @Override
    public boolean isOutOfStock() {
        return 0 == this.quantityAvailable;
    }
}
