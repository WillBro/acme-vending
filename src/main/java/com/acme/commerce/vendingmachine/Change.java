package com.acme.commerce.vendingmachine;

/**
 * @author William Brown
 * @since 1.0
 */
public enum Change {
    ONE_PENCE(1), TWO_PENCE(2), FIVE_PENCE(5), TEN_PENCE(10),
    TWENTY_PENCE(20), FIFTY_PENCE(50), ONE_POUND(100), TWO_POUND(200);

    private int value;

    Change(int value) {
        this.value = value;
    }
}
