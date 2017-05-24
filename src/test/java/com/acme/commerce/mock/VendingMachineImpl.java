package com.acme.commerce.mock;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.VendingMachine;

import java.util.*;

/**
 * @author William Brown
 * @since 1.0
 */
public class VendingMachineImpl implements VendingMachine {
    private Map<Change, Integer> changeAvailable = new EnumMap<>(Change.class);
    private Map<Change, Integer> changeInserted = new HashMap<>();

    public VendingMachineImpl() {
        this.changeInserted = new HashMap<>();
        this.changeAvailable = new HashMap<>();

        addFiveOfEachChangeToAvailableChange();
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
    public void refundChange() {

    }

    @Override
    public void reset() {

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

    /**
     * Convenience to setup the vending machine with reasonable change
     */
    private void addFiveOfEachChangeToAvailableChange() {
        this.changeAvailable.put(Change.ONE_PENCE, 5);
        this.changeAvailable.put(Change.TWO_PENCE, 5);
        this.changeAvailable.put(Change.FIVE_PENCE, 5);
        this.changeAvailable.put(Change.TEN_PENCE, 5);
        this.changeAvailable.put(Change.TWENTY_PENCE, 5);
        this.changeAvailable.put(Change.FIFTY_PENCE, 5);
        this.changeAvailable.put(Change.ONE_POUND, 5);
        this.changeAvailable.put(Change.TWO_POUND, 5);
    }
}