package com.acme.commerce;

import com.acme.commerce.mock.VendingMachineImpl;
import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.VendingMachine;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author William Brown
 * @since 1.0
 */
public class VendingMachineTest {
    @Test
    public void addingMoneyUpdatesTotal() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        int changeRunningTotal = 0;

        vendingMachine.insertChange(Change.FIVE_PENCE);
        vendingMachine.insertChange(Change.ONE_PENCE);
        vendingMachine.insertChange(Change.TWO_PENCE);
        vendingMachine.insertChange(Change.TWO_PENCE);
        vendingMachine.insertChange(Change.TWO_PENCE);
        vendingMachine.insertChange(Change.TWO_PENCE);
        changeRunningTotal = vendingMachine.insertChange(Change.ONE_POUND);

        assertEquals(5+1+2+2+2+2+100, changeRunningTotal);
    }
}
