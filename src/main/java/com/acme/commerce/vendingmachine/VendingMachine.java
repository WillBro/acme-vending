package com.acme.commerce.vendingmachine;

import com.acme.commerce.vendingmachine.exception.ChangeNotAcceptedException;
import com.acme.commerce.vendingmachine.exception.InsufficientChangeException;
import com.acme.commerce.vendingmachine.exception.OutOfStockException;


import java.util.List;
import java.util.Map;

/**
 * @author William Brown
 * @since 1.0
 */
public interface VendingMachine {
    List<Change> getAcceptedChange();
    boolean isPoweredOn();
    void powerOn();
    void powerOff();
    int getBalance();
    Map<Change, Integer> getChangeAvailable();
    int insertChange(Change change) throws ChangeNotAcceptedException;
    List<Change> getChangeInserted();
    int refundChange();
    void reset();
    void purchase(Product product) throws OutOfStockException, InsufficientChangeException;
    Map<String, Product> getProductList();
}
