package com.acme.commerce;

import com.acme.commerce.vendingmachine.Change;
import com.acme.commerce.vendingmachine.Product;
import com.acme.commerce.vendingmachine.ProductFactory;
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
    public void defaultVendingMachineIsOff() {
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
    public void thereAreThreeProductsByDefault() {
        VendingMachine vendingMachine = new VendingMachineImpl(true);

        assert (3 == vendingMachine.getProductList().size());
    }

    @Test
    public void theDefaultProductsMeetRequirements() {
        // Possibly better a functional test?
        VendingMachine vendingMachine = new VendingMachineImpl(true);

        Map<String, Product> defaultProducts = getDefaultProducts();

        assert(defaultProducts.equals(vendingMachine.getProductList()));
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

        try {
            vendingMachine.insertChange(Change.ONE_PENCE);

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

        assert (acceptedChange.equals(expectedList));
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


        Product outOfStockProduct = ProductFactory.createProduct("An Out of stock product ", 60, 0);

        try {
            purchaseTransaction(vendingMachine, outOfStockProduct, Change.FIFTY_PENCE);
        } catch (OutOfStockException | ChangeNotAcceptedException e) {
            // @todo Determine if this is the best approach
            System.out.println(e.getMessage());
        } catch (InsufficientChangeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void canBuyInStockProduct() {
        VendingMachine vendingMachine = new VendingMachineImpl();

        Product anInstockProduct = ProductFactory.createProduct("An Out of stock product ", 60, 10);

        try {
            purchaseTransaction(vendingMachine, anInstockProduct, Change.FIFTY_PENCE);
        } catch (OutOfStockException | ChangeNotAcceptedException e) {
            // @todo Determine if this is the best approach
            System.out.println(e.getMessage());
        } catch (InsufficientChangeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void cannotBuyIfNotEnoughChange() {
        VendingMachine vendingMachine = new VendingMachineImpl(true);

        Product aProduct = ProductFactory.createProduct("A product", 170);

        try {
            purchaseTransaction(vendingMachine, aProduct, Change.FIFTY_PENCE);
        } catch (OutOfStockException | ChangeNotAcceptedException e) {
            // @todo Determine if this is the best approach
            System.out.println(e.getMessage());
        } catch (InsufficientChangeException e) {
            System.out.println(e.getMessage());
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

    /**
     * Get default selection of products as per user requirements
     *
     * @return
     */
    @Ignore
    protected Map<String, Product> getDefaultProducts() {
        Product aProduct = ProductFactory.createProduct("A nice product", 60, 10);
        Product bProduct = ProductFactory.createProduct("A berry nice product", 100, 4);
        Product cProduct = ProductFactory.createProduct("A cherry berry nice product", 170, 1);

        Map<String, Product> availableProducts = new HashMap<>();
        availableProducts.put("A", aProduct);
        availableProducts.put("B", bProduct);
        availableProducts.put("C", cProduct);

        return availableProducts;
    }
}
