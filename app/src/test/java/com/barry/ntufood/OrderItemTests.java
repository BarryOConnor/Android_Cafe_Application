package com.barry.ntufood;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class OrderItemTests {
    @Test
    public void testConstructorName(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        assertEquals(mItem.getItemName(),"test");
    }

    @Test
    public void testConstructorPrice(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        assertEquals(mItem.getPrice(),2.00,0);
    }

    @Test
    public void testConstructorAmount(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        assertEquals(mItem.getAmount(),2);
    }

    @Test
    public void testConstructorTotal(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        assertEquals(mItem.getTotal(),4.00,0);
    }

    @Test
    public void testGetSetName(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        mItem.setItemName("newValue");
        assertEquals(mItem.getItemName(),"newValue");
    }

    @Test
    public void testGetSetPrice(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        mItem.setPrice(4.45);
        assertEquals(mItem.getPrice(),4.45,0);
    }

    @Test
    public void testGetSetAmount(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        mItem.setAmount(5);
        assertEquals(mItem.getAmount(),5);
    }

    @Test
    public void testGetSetTotal(){
        OrderItem mItem = new OrderItem("test", 2.00, 2, 4.00);
        mItem.setTotal(14.99);
        assertEquals(mItem.getTotal(),14.99,0);
    }

    @Test
    public void testAddSingleAddition(){
        OrderItem mItem = new OrderItem();
        mItem.addAddition("test 1", 5.45);
        assertEquals(mItem.getAdditions().size(),1);
    }

    @Test
    public void testAddTwoAddition(){
        OrderItem mItem = new OrderItem();
        mItem.addAddition("test 1", 5.45);
        mItem.addAddition("test 2", 5.45);
        assertEquals(mItem.getAdditions().size(),2);
    }

    @Test
    public void testAddMultipleAddition(){
        OrderItem mItem = new OrderItem();
        mItem.addAddition("test 1", 5.45);
        mItem.addAddition("test 2", 5.45);
        mItem.addAddition("test 3", 5.45);
        mItem.addAddition("test 4", 5.45);
        mItem.addAddition("test 5", 5.45);
        mItem.addAddition("test 6", 5.45);
        mItem.addAddition("test 7", 5.45);
        mItem.addAddition("test 8", 5.45);
        mItem.addAddition("test 9", 5.45);
        mItem.addAddition("test 10", 5.45);
        assertEquals(mItem.getAdditions().size(),10);
    }


    @Test
    public void testAddAddition(){
        OrderItem mItem = new OrderItem();
        HashMap<String, Double> additions = new HashMap<String, Double>();
        additions.put("test 1", 5.45);
        additions.put("test 2", 5.45);
        additions.put("test 3", 5.45);
        additions.put("test 4", 5.45);
        additions.put("test 5", 5.45);

        mItem.setAdditions(additions);

        assertEquals(mItem.getAdditions().size(),5);
    }
}
