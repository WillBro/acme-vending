package com.acme.commerce;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.VendingMachine;
import com.acme.commerce.vendingmachine.exception.ChangeNotAcceptedException;
import com.acme.commerce.vendingmachine.exception.InsufficientChangeException;
import com.acme.commerce.vendingmachine.exception.OutOfStockException;
import com.acme.commerce.vendingmachine.impl.VendingMachineImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author William Brown
 * @since 1.0
 */
public class VendingMachineTest {

    @Test
    public void defaultVendingMachineIsOff()
    {
        VendingMachine vendingMachine = new VendingMachineImpl();

        assertFalse(vendingMachine.isPoweredOn());
    }

    @Test
    public void turnsOnWhenOff() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        boolean isOn = vendingMachine.isPoweredOn();

        assertFalse(isOn); // potentially redundant as default constructor is tested above?

        vendingMachine.powerOn();

        assertTrue(vendingMachine.isPoweredOn());
    }

    @Test
    public void turnsOffWhenOn() {
        VendingMachine vendingMachine = new VendingMachineImpl(true); // already on
        assertTrue(vendingMachine.isPoweredOn());

        vendingMachine.powerOff();

        assertFalse(vendingMachine.isPoweredOn());
    }

    @Test
    public void addingMoneyUpdatesTotal() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        int changeRunningTotal = 0;

        try {
            vendingMachine.insertChange(Change.TEN_PENCE);
            vendingMachine.insertChange(Change.TEN_PENCE);
            vendingMachine.insertChange(Change.TEN_PENCE);
            vendingMachine.insertChange(Change.TEN_PENCE);
            vendingMachine.insertChange(Change.TEN_PENCE);
            vendingMachine.insertChange(Change.TEN_PENCE);
            changeRunningTotal = vendingMachine.insertChange(Change.ONE_POUND);
        } catch (ChangeNotAcceptedException e) {
            // Hopefully we're inserting all the correct change in this test.
        }

        assertEquals(60 + 100, changeRunningTotal);
        assertEquals(10 + 10 + 10 + 10 + 10 + 10 + 100, vendingMachine.getBalance()); // Should both be the same result
    }

    @Test
    public void addingUnacceptedChangeIsNotAllowed() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        int changeRunningTotal = 0;

        try {
            changeRunningTotal = vendingMachine.insertChange(Change.ONE_PENCE);

            fail("ChangeNotAcceptedException was expected.");
        } catch (ChangeNotAcceptedException e) {
            // Empty body - exists due to fail above being executed only when we don't arrive in this exception.
        }
    }

    @Test
    public void defaultAcceptedCoinCountIsFour() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        List<Change> acceptedChange = vendingMachine.getAcceptedChange();

        assertTrue(4 == acceptedChange.size());
    }

    @Test
    public void defaultAcceptedCoinList() {
        List<Change> expectedList = new ArrayList<>();
        expectedList.add(Change.TEN_PENCE);
        expectedList.add(Change.TWENTY_PENCE);
        expectedList.add(Change.FIFTY_PENCE);
        expectedList.add(Change.ONE_POUND);

        VendingMachine vendingMachine = new VendingMachineImpl();
        List<Change> acceptedChange = vendingMachine.getAcceptedChange();

        assert(acceptedChange.equals(expectedList));
    }

    @Test
    public void refundingResetsChangeInMachineToZero() {
        VendingMachine vendingMachine = new VendingMachineImpl();
        try {
            vendingMachine.insertChange(Change.FIFTY_PENCE);
        } catch (ChangeNotAcceptedException e) {
            // @todo Determine what to do in this instance.
        }

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

        try {
            vendingMachine.insertChange(Change.TWO_POUND);
        } catch (ChangeNotAcceptedException e) {
            // Purposefully left blank - for now.

        }

        try {
            vendingMachine.purchase(productCola);
        } catch (OutOfStockException eOos) {
            // Purposefully left blank - for now.
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

        try {
            vendingMachine.insertChange(Change.TWO_POUND);
        } catch (ChangeNotAcceptedException e) {
            // @todo Log?
        }

        try {
            vendingMachine.purchase(anInstockProduct);
        } catch (OutOfStockException e) {
            // @todo Log?
        }
    }

    @Test
    public void cannotBuyIfNotEnoughChange() {
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

        try {
            purchaseTransaction(vendingMachine, anExpensiveProduct, Change.TWO_POUND);

            vendingMachine.purchase(anExpensiveProduct);
        } catch (OutOfStockException|InsufficientChangeException|ChangeNotAcceptedException e) {
            // @todo Determine if this is the best approach
        }
    }

    /**
     * Convenience method to purchasing a product using a single coin
     *
     * @param vendingMachine
     * @param product
     * @param change
     * @throws OutOfStockException
     * @throws InsufficientChangeException
     * @throws ChangeNotAcceptedException
     */
    @Ignore
    protected void purchaseTransaction(VendingMachine vendingMachine, Product product, Change change) throws OutOfStockException, InsufficientChangeException, ChangeNotAcceptedException {
        Map<Change, Integer> listOfChange = new HashMap<>();
        listOfChange.put(change, 1);

        purchaseTransaction(vendingMachine, product, listOfChange);
    }


    /**
     * Convenience to buy a product using multiple added coins
     *
     * @param vendingMachine
     * @param product
     * @param insertChange
     * @throws OutOfStockException
     * @throws InsufficientChangeException
     * @throws ChangeNotAcceptedException
     */
    @Ignore
    protected void purchaseTransaction(VendingMachine vendingMachine, Product product, Map<Change, Integer> insertChange) throws OutOfStockException, InsufficientChangeException, ChangeNotAcceptedException {
        for (Map.Entry<Change, Integer> entry : insertChange.entrySet()) {
            Change change = entry.getKey();
            Integer countCoins = entry.getValue();

            for (int i = 0; i < countCoins; i++) {
                vendingMachine.insertChange(change);
            }
        }

        vendingMachine.purchase(product);
    }
}
