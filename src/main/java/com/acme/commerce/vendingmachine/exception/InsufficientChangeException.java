package com.acme.commerce.vendingmachine.exception;

/**
 * Insufficient Change Exception for when the customer has not inserted enough money
 * to purchase their product
 *
 * @author William Brown
 * @since 1.0
 */
public class InsufficientChangeException extends Exception {

    private int errCode;

    public InsufficientChangeException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
