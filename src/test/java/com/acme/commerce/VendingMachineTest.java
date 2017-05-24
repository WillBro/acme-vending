package com.acme.commerce;

import com.acme.commerce.vendingmachine.exception.InsufficientChangeException;
import com.acme.commerce.vendingmachine.impl.VendingMachineImpl;
import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.VendingMachine;
import com.acme.commerce.vendingmachine.exception.OutOfStockException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Assert;
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

        assertEquals(5 + 1 + 2 + 2 + 2 + 2 + 100, changeRunningTotal);
    }

    @Test
    public void refundingResetsChangeInMachineToZero() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        vendingMachine.insertChange(Change.FIFTY_PENCE);

        int machineChangeAfterRefund = vendingMachine.refundChange();

        assertEquals(0, machineChangeAfterRefund);
    }

    @Test()
    public void cannotBuyOutOfStockProduct() {
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

        } catch (Exception e) {
            // Purposefully lefy blank.
        }
    }

    @Test
    public void canBuyInStockProduct() {
        VendingMachine vendingMachine = new VendingMachineImpl();

        // @todo Better reuse from an inner class (or fixtures) as this is code duplication
        Product anInstockProduct = new Product() {
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
                return 5;
            }

            @Override
            public boolean isOutOfStock() {
                return getQuantityAvailable() == 0;
            }
        };

        vendingMachine.insertChange(Change.TWO_POUND);

        try {
            vendingMachine.purchase(anInstockProduct);
        } catch (Exception e) {

        }
    }

    @Test
    public void cannotBuyIfNotEnoughChange()
    {
        VendingMachine vendingMachine = new VendingMachineImpl();

        // @todo Better reuse from an inner class (or fixtures) as this is code duplication
        Product anExpensiveProduct = new Product() {
            @Override
            public String getName() {
                return "Really Expensive Produc";
            }

            @Override
            public int getCost() {
                return 1299;
            }

            @Override
            public int getQuantityAvailable() {
                return 5;
            }

            @Override
            public boolean isOutOfStock() {
                return getQuantityAvailable() == 0;
            }
        };

        vendingMachine.insertChange(Change.TWO_POUND);

        try {
            vendingMachine.purchase(anExpensiveProduct);
        } catch (InsufficientChangeException e) {
            // @todo
        } catch (OutOfStockException oe) {
            fail("InsufficientChangeException In");
        }
    }
}
