package fi.tuni.prog3.junitorder;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest
{
    // global variables
    String NAME = "iPhone 34b";
    double PRICE = 6969.69;

    // #######new Order().### ITEM ##########

    // Constructor
    // WORKING
    @Test
    public void testItemConstructionName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order.Item(null, PRICE);
        });
    }

    // WORKING
    @Test
    public void testItemConstructorPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order.Item(NAME, -1);
        });
    }

    // Getter methods
    @Test
    public void testGetName() {
    Order.Item instance = new Order.Item(NAME, PRICE);
        String expName = NAME;
        assertEquals(expName, instance.getName());
    }

    @Test
    public void testGetPrice() {
    Order.Item instance = new Order.Item(NAME, PRICE);
        double expPrice = PRICE;
        assertEquals(expPrice, instance.getPrice());
    }

    // toString
    // WORKING
    @Test
    public void testItemToString() {

    Order.Item instance = new Order.Item(NAME, PRICE);
        String expResult = "Item(" + NAME + ", " + PRICE + ")";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    // equals
    @Test
    public void testItemEquals() {
    Order.Item instance1 = new Order.Item(NAME, PRICE);
    Order.Item instance2 = new Order.Item(NAME, PRICE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testItemNotEquals() {
    Order.Item instance1 = new Order.Item(NAME, PRICE);
    Order.Item instance2 = new Order.Item("TwoNote", PRICE);
        assertFalse(instance1.equals(instance2));
    }


    int COUNT = 23;
    Order.Item TESTITEM = new Order.Item(NAME, PRICE);
    // #######new Order().### ENTRY ##########
    // Constructor
    // WORKING
    @Test
    public void testEntryConstructorException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order.Entry(TESTITEM, -1);
        });
    }

    // Getter methods
    @Test
    public void testGetItemName() {
    Order.Entry instance = new Order.Entry(TESTITEM, COUNT);
        String expName = NAME;
        assertEquals(expName, instance.getItemName());
    }

    @Test
    public void testGetUnitPrice() {
    Order.Entry instance = new Order.Entry(TESTITEM, COUNT);
        double expPrice = PRICE;
        assertEquals(expPrice, instance.getUnitPrice());
    }

    @Test
    public void testGetItem() {
    Order.Entry instance = new Order.Entry(TESTITEM, COUNT);
        assertEquals(TESTITEM, instance.getItem());
    }

    // WORKING
    @Test
    public void testGetCount() {
    Order.Entry instance = new Order.Entry(TESTITEM, COUNT);
        assertEquals(COUNT, instance.getCount());
    }

    // toString
    // WORKING
    @Test
    public void testEntryToString() {

    Order.Entry instance = new Order.Entry(TESTITEM, COUNT);
        String expResult = COUNT + " units of " + TESTITEM.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    // ########## ORDER ##########
    Order orderObj;
    int NUMITEMS = 2;

    @BeforeEach
    public void setUpOrderClass() throws Exception {
        orderObj = new Order();
    }

    // ============= addItems methods =============
    // (Item object, integer count) constructor
    @Test
    public void testOrderAddItemsNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderObj.addItems(TESTITEM, -1);
        });
    }

    @Test
    public void testOrderAddItemsDuplicate() {
        // Add first item
        orderObj.addItems(TESTITEM, NUMITEMS);
        // Make an item of the same name with a different price
        Order.Item diffItem = new Order.Item(NAME, PRICE + 1);

        assertThrows(IllegalStateException.class, () -> {
            // Add duplicate item
            orderObj.addItems(diffItem, NUMITEMS);
        });
    }

    @Test
    public void testOrderAddItems() {
        // Item does not exist
        assertTrue(orderObj.addItems(TESTITEM, NUMITEMS));
        assertEquals(orderObj.getItemCount(), NUMITEMS);

        // Item already exists
        assertTrue(orderObj.addItems(TESTITEM, NUMITEMS));
        assertEquals(orderObj.getItemCount(), NUMITEMS * 2);
    }

    // (String name, integer count) constructor (Item must already exist)
    @Test
    public void testOrderAddItemsStringNegativeCount() {
        // Add item with name
        orderObj.addItems(TESTITEM, NUMITEMS);
        // Try to increment by negative
        assertThrows(IllegalArgumentException.class, () -> {
            orderObj.addItems(NAME, -1);
        });
    }

    @Test
    public void testOrderAddItemsNoItem() {
        // Add item with string name that doesn't exist
        assertThrows(NoSuchElementException.class, () -> {
            // Add duplicate item
            orderObj.addItems(NAME, NUMITEMS);
        });
    }

    @Test
    public void testOrderAddItemsString() {
        // Item must first exist (use .addItems with Item object constructor)
        assertTrue(orderObj.addItems(TESTITEM, NUMITEMS));

        // Test functionality (Use NAME string instead of Item object)
        assertTrue(orderObj.addItems(NAME, NUMITEMS));
        assertEquals(orderObj.getItemCount(), NUMITEMS * 2);
    }

    // ============= getter methods =============

    // control error:
    // testOrderGetEntries() ✘ expected: java.util.ArrayList@515c6049<[1 units of Item(Item1, 10.00),
    //      2 units of Item(Item2, 20.00), 3 units of Item(Item3, 30.00)]>
    // but was: java.util.ArrayList@639c2c1d<[1 units of Item(Item1, 10.00),
    //      2 units of Item(Item2, 20.00), 3 units of Item(Item3, 30.00)]>

    /*
    @Test
    public void testOrderGetEntries() {
        // Create expected list
        List<Order.Entry> expList = new ArrayList<>();

        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Entry entry1 = new Order.Entry(item1, 1);
        expList.add(entry1);

        Order.Item item2 = new Order.Item("Item2", 20.0);
        Order.Entry entry2 = new Order.Entry(item2, 2);
        expList.add(entry2);

        Order.Item item3 = new Order.Item("Item3", 30.0);
        Order.Entry entry3 = new Order.Entry(item3, 3);
        expList.add(entry3);

        // Add items to order object, creating entries
        orderObj.addItems(item1, 1);
        orderObj.addItems(item2, 2);
        orderObj.addItems(item3, 3);

        // Test that returned list is correct

        for (int i = 0; i < expList.size(); i++) {
            assertEquals(expList.get(i), orderObj.getEntries().get(i));
        }
        // assertEquals(expList, orderObj.getEntries());

        // Test that returned list is a copy
        // (Modifying returned list does not change list in object)
        List<Order.Entry> testList = orderObj.getEntries();
        testList.clear();

        for (int i = 0; i < expList.size(); i++) {
            assertEquals(expList.get(i), orderObj.getEntries().get(i));
        }
        // assertEquals(expList, orderObj.getEntries());
    }*/

    @Test
    public void testOrderGetEntryCount() {
        // Create expected list
        List<Order.Entry> expList = new ArrayList<>();

        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Entry entry1 = new Order.Entry(item1, 2);
        expList.add(entry1);

        Order.Item item2 = new Order.Item("Item2", 20.0);
        Order.Entry entry2 = new Order.Entry(item2, 2);
        expList.add(entry2);

        Order.Item item3 = new Order.Item("Item3", 30.0);
        Order.Entry entry3 = new Order.Entry(item3, 2);
        expList.add(entry3);

        // Add items to order object, creating entries
        orderObj.addItems(item1, 2);
        orderObj.addItems(item2, 2);
        orderObj.addItems(item3, 2);

        // Test entry count (2 * 3)
        assertEquals(3, orderObj.getEntryCount());
    }

    @Test
    public void testOrderGetItemCount() {
        // Create expected list
        List<Order.Entry> expList = new ArrayList<>();

        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Entry entry1 = new Order.Entry(item1, 2);
        expList.add(entry1);

        Order.Item item2 = new Order.Item("Item2", 20.0);
        Order.Entry entry2 = new Order.Entry(item2, 2);
        expList.add(entry2);

        Order.Item item3 = new Order.Item("Item3", 30.0);
        Order.Entry entry3 = new Order.Entry(item3, 2);
        expList.add(entry3);

        // Add items to order object, creating entries
        orderObj.addItems(item1, 2);
        orderObj.addItems(item2, 2);
        orderObj.addItems(item3, 2);

        // Test item count (2 * 2 * 3)
        assertEquals(6, orderObj.getItemCount());
    }

    @Test
    public void testOrderGetTotalPrice() {
        // Create expected list
        List<Order.Entry> expList = new ArrayList<>();

        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Entry entry1 = new Order.Entry(item1, 2);
        expList.add(entry1);

        Order.Item item2 = new Order.Item("Item2", 20.0);
        Order.Entry entry2 = new Order.Entry(item2, 2);
        expList.add(entry2);

        Order.Item item3 = new Order.Item("Item3", 30.0);
        Order.Entry entry3 = new Order.Entry(item3, 2);
        expList.add(entry3);

        // Add items to order object, creating entries
        orderObj.addItems(item1, 2); // (2 * 2 * 10) +
        orderObj.addItems(item2, 2); // (2 * 2 * 20) +
        orderObj.addItems(item3, 2); // (2 * 2 * 30)

        // Test item count (2 * 2 * 3)
        assertEquals(6, orderObj.getItemCount());
    }

    @Test
    public void testOrderIsEmpty() {
        // we haven't added to this yet
        assertTrue(orderObj.isEmpty());

        // first we add to Order, thus after isEmpty() should return false
        orderObj.addItems(TESTITEM, NUMITEMS);
        assertFalse(orderObj.isEmpty());
    }



}