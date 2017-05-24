package com.acme.commerce.vendingmachine.impl;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.VendingMachine;
import com.acme.commerce.vendingmachine.exception.ChangeNotAcceptedException;
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
    private List<Change> acceptedCoins = new ArrayList<>();
//    private static final ImmutableList<Change> acceptedChangeList = new ImmutableList.Builder<Change>()
//            .add(Change.TEN_PENCE)
//            .add(Change.TWENTY_PENCE)
//            .add(Change.FIFTY_PENCE)
//            .add(Change.ONE_POUND)
//        .build();


    /**
     * Default Vending Machine with 5 of all change types
     */
    public VendingMachineImpl() {
        this.isPoweredOn = false;
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
    public int insertChange(Change change) throws ChangeNotAcceptedException {
        if (!acceptsChangeDenomination(change)) {
            throw new ChangeNotAcceptedException();
        }

        addChange(change);

        return calculateChangeInserted();
    }

    /**
     * Does this machine accept the inserted coin type?
     *
     * @param change
     * @return
     */
    public boolean acceptsChangeDenomination(Change change) {
        return getAcceptedChange().contains(change);
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
