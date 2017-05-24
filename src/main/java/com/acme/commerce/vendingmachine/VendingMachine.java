package com.acme.commerce.vendingmachine;

import java.util.List;
import java.util.Map;

/**
 * @author William Brown
 * @since 1.0
 */
public interface VendingMachine {
    Map<Change, Integer> getChangeAvailable();
    long insertChange(Change c);
    List<Change> getChangeInserted();
    void refundChange();
    void reset();
}
