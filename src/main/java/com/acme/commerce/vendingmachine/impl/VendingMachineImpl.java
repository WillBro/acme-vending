package com.acme.commerce.vendingmachine.impl;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.VendingMachine;
import com.acme.commerce.vendingmachine.exception.OutOfStockException;

import java.util.*;

/**
 * Vending Machine Implementation
 *
 * @author William Brown
 * @since 1.0
 */
public class VendingMachineImpl implements VendingMachine {
    private boolean isPoweredOn = false;
    private Map<Change, Integer> changeAvailable = new EnumMap<>(Change.class);
    private Map<Change, Integer> changeInserted = new HashMap<>();

    /**
     * Default Vending Machine with 5 of all change types
     */
    public VendingMachineImpl() {
        this.isPoweredOn = false;
        this.changeInserted = new HashMap<>();
        this.changeAvailable = new HashMap<>();

        this.changeAvailable.put(Change.ONE_PENCE, 5);
        this.changeAvailable.put(Change.TWO_PENCE, 5);
        this.changeAvailable.put(Change.FIVE_PENCE, 5);
        this.changeAvailable.put(Change.TEN_PENCE, 5);
        this.changeAvailable.put(Change.TWENTY_PENCE, 5);
        this.changeAvailable.put(Change.FIFTY_PENCE, 5);
        this.changeAvailable.put(Change.ONE_POUND, 5);
        this.changeAvailable.put(Change.TWO_POUND, 5);
    }

    /**
     * Default, powered on, vending machine
     *
     * @param isOn boolean Initial power state of the new machine
     */
    public VendingMachineImpl(boolean isOn) {
        super();

        this.isPoweredOn = isOn;
    }

    @Override
    public boolean isPoweredOn() {
        return this.isPoweredOn;
    }

    @Override
    public void powerOn() {
        this.isPoweredOn = true;
    }

    @Override
    public void powerOff() {
        this.isPoweredOn = false;
    }

    @Override
    public Map<Change, Integer> getChangeAvailable() {
        return changeAvailable;
    }

    @Override
    public int insertChange(Change change) {
        addChange(change);

        return calculateChangeInserted();
    }

    @Override
    public List<Change> getChangeInserted() {
        return null;
    }

    @Override
    public int refundChange() {
        this.changeInserted.clear();

        return calculateChangeInserted();
    }

    @Override
    public void reset() {
        this.changeInserted.clear();
        this.changeAvailable.clear();
    }

    @Override
    public void purchase(Product product) throws OutOfStockException {
        if (product.isOutOfStock()) {
            throw new OutOfStockException(100, "Sorry this product is out of stock");
        }
    }

    /**
     * Add to the count of the type of change inserted (ie. how many FIVE_PENCES have been added)
     *
     * @param change
     */
    private void addChange(Change change)
    {
        if (this.changeInserted.containsKey(change)) {
            this.changeInserted.put(change, this.changeInserted.get(change) + 1);
        } else {
            this.changeInserted.put(change, 1);
        }
    }

    /**
     * Calculate, in pennies, the total value of change inserted
     * @return int Running total of value of inserted change
     */
    private int calculateChangeInserted() {
        int total = 0;

        Iterator it = this.changeInserted.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            int numberOfChangeType = (Integer) pair.getValue();
            Change changeType =  Change.valueOf(pair.getKey().toString());
            int valueOfChange = changeType.getValue();

            total = total + (numberOfChangeType * valueOfChange);
        }

        return total;
    }
}
