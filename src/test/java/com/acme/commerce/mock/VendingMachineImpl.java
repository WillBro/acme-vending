package com.acme.commerce.mock;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.VendingMachine;

import java.util.List;
import java.util.Map;

/**
 * @author William Brown
 * @since 1.0
 */
public class VendingMachineImpl implements VendingMachine {
    @Override
    public Map<Change, Integer> getChangeAvailable() {
        return null;
    }

    @Override
    public long insertChange(Change c) {
        return 0;
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
}
