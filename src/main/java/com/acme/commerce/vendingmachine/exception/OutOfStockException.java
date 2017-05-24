package com.acme.commerce.vendingmachine.exception;

/**
 * Out Of Stock Exception
 *
 * @author William Brown
 * @since 1.0
 */
public class OutOfStockException extends Exception {

    private int errCode;

    public OutOfStockException(int errCode, String message) {
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
