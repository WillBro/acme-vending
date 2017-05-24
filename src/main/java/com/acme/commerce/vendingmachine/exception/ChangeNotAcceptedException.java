package com.acme.commerce.vendingmachine.exception;

/**
 * @author William Brown
 * @since 1.0
 */
public class ChangeNotAcceptedException extends Exception {
    public ChangeNotAcceptedException() {
        super("We do not accept that coin.");
    }
}
