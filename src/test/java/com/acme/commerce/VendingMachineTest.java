package com.acme.commerce;

import com.acme.commerce.mock.VendingMachineImpl;
import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.VendingMachine;
import com.acme.commerce.vendingmachine.exception.OutOfStockException;
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

    @Test()
    public void cannotBuyOutOfStockProduct()
    {
        VendingMachine vendingMachine = new VendingMachineImpl();
        Product productCola = new Product() {
            @Override
            public String getName() {
                return "Coca Cola";
            }

            @Override
            public int getCost() {
                return 120;
            }

            @Override
            public int getQuantityAvailable() {
                return 0;
            }

            @Override
            public boolean isOutOfStock() {
                return getQuantityAvailable() == 0;
            }
        };

        vendingMachine.insertChange(Change.TWO_POUND);

        try {
            vendingMachine.purchase(productCola);

            fail("Expected OutOfStockException");

        } catch (OutOfStockException e) {
            // Purposefully lefy blank.
        }
    }
}
