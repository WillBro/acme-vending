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
    private Map<Change, Integer> changeAvailable = new EnumMap<>(Change.class);
    private Map<Change, Integer> changeInserted = new HashMap<>();
    private List<Change> acceptedCoins = new ArrayList<>();
//    private static final ImmutableList<Change> acceptedChangeList = new ImmutableList.Builder<Change>()
//            .add(Change.TEN_PENCE)
//            .add(Change.TWENTY_PENCE)
//            .add(Change.FIFTY_PENCE)
//            .add(Change.ONE_POUND)
//        .build();


    public VendingMachineImpl() {
        this.changeInserted = new HashMap<>();
        this.changeAvailable = new HashMap<>();

        this.changeAvailable.put(Change.TEN_PENCE, 5);
        this.changeAvailable.put(Change.TWENTY_PENCE, 5);
        this.changeAvailable.put(Change.FIFTY_PENCE, 5);
        this.changeAvailable.put(Change.ONE_POUND, 5);

        this.acceptedCoins = new ArrayList<>();
        this.acceptedCoins.add(Change.TEN_PENCE);
        this.acceptedCoins.add(Change.TWENTY_PENCE);
        this.acceptedCoins.add(Change.FIFTY_PENCE);
        this.acceptedCoins.add(Change.ONE_POUND);
    }

    @Override
    public List<Change> getAcceptedChange() {
        return this.acceptedCoins;
    }

    @Override
    public int getBalance() {
        return calculateChangeInserted();
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
    public List<Change> getChangeInserted() throws RuntimeException {
        throw new RuntimeException("Not implemented yet");
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
    private void addChange(Change change) {
        if (this.changeInserted.containsKey(change)) {
            this.changeInserted.put(change, this.changeInserted.get(change) + 1);
        } else {
            this.changeInserted.put(change, 1);
        }
    }

    /**
     * Calculate, in pennies, the total value of change inserted
     *
     * @return int Running total of value of inserted change
     */
    private int calculateChangeInserted() {
        int total = 0;

        Iterator it = this.changeInserted.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            int numberOfChangeType = (Integer) pair.getValue();
            Change changeType = Change.valueOf(pair.getKey().toString());
            int valueOfChange = changeType.getValue();

            total = total + (numberOfChangeType * valueOfChange);
        }

        return total;
    }
}
