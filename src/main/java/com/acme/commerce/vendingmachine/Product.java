package com.acme.commerce.vendingmachine;

/**
 * Product Interface
 *
 * @author William Brown
 * @since 1.0
 */
public interface Product {
    String getName();
    int getCost();
    int getQuantityAvailable();
    boolean isOutOfStock();
}
