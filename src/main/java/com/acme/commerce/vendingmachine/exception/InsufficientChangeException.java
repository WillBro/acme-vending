package com.acme.commerce.vendingmachine.exception;

/**
 * Insufficient Change Exception for when the customer has not inserted enough money
 * to purchase their product
 *
 * @author William Brown
 * @since 1.0
 */
public class InsufficientChangeException extends Exception {

    public InsufficientChangeException(String message) {
        super(message);
    }
}
