package com.acme.commerce.vendingmachine.exception;

/**
 * When the user interaction clearly won't work (driving a car when its not on).
 * Possibly belongs in org.humans.exception as its not the machines fault we do surprising things
 *
 * @author William Brown
 * @since 1.0
 */
public class HumanException extends Exception {

    public HumanException(String message) {
        super(message);
    }
}
