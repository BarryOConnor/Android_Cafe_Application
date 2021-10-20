package com.barry.ntufood;

import com.google.firebase.Timestamp;

import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTests {

    @Test
    public void testSetUser(){
        Order mOrder = new Order();
        mOrder.setUserID("xxxxxxxxxxxx");
        assertEquals(mOrder.getUserID(),"xxxxxxxxxxxx");
    }

    @Test
    public void testSetOutlet(){
        Order mOrder = new Order();
        mOrder.setOutlet("Clifton Barista");
        assertEquals(mOrder.getOutlet(),"Clifton Barista");
    }

    @Test
    public void testSetCompleteTrue(){
        Order mOrder = new Order();
        mOrder.setIsComplete(true);
        assertEquals(mOrder.getIsComplete(),true);
    }

    @Test
    public void testSetCompleteDefault(){
        Order mOrder = new Order();
        assertEquals(mOrder.getIsComplete(),false);
    }

    @Test
    public void testSetTable(){
        Order mOrder = new Order();
        mOrder.setTable("21");
        assertEquals(mOrder.getTable(),"21");
    }

    @Test
    public void testTimeStamp() throws ParseException {
        Order mOrder = new Order();
        Timestamp now = Timestamp.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY HH:mm:ss");
        mOrder.setDatetime(now);
        assertEquals(mOrder.timestampFormatted(),dateFormat.format(now.toDate()));
    }

    @Test
    public void testSetItems(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemThree = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemFour = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemFive = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemSix = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        List<OrderItem> mItems = new ArrayList<OrderItem>();

        mItems.add(mItemOne);
        mItems.add(mItemTwo);
        mItems.add(mItemThree);
        mItems.add(mItemFour);
        mItems.add(mItemFive);
        mItems.add(mItemSix);

        mOrder.setItems(mItems);

        assertEquals(mOrder.getItems().size(),6);
    }



    @Test
    public void testAdditionOneItemWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        mOrder.addItem(mItemOne);
        assertEquals(mOrder.getItems().size(),1);
    }

    @Test
    public void testAdditionTwoItemsWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        assertEquals(mOrder.getItems().size(),2);
    }

    @Test
    public void testAdditionMultipleItemsWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemThree = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemFour = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemFive = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemSix = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        mOrder.addItem(mItemThree);
        mOrder.addItem(mItemFour);
        mOrder.addItem(mItemFive);
        mOrder.addItem(mItemSix);

        assertEquals(mOrder.getItems().size(),6);
    }

    @Test
    public void testTotalOneItemWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        mOrder.addItem(mItemOne);
        assertEquals(mOrder.getTotal(),3.00,0);
    }

    @Test
    public void testTotalItemsWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        assertEquals(mOrder.getTotal(),7.00, 0);
    }

    @Test
    public void testTotalMultipleItemsWithAddition(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemThree = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemFour = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemFive = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemSix = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        mOrder.addItem(mItemThree);
        mOrder.addItem(mItemFour);
        mOrder.addItem(mItemFive);
        mOrder.addItem(mItemSix);

        assertEquals(mOrder.getTotal(),21.00, 0);
    }

    @Test
    public void testSizeAfterRemove(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemThree = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemFour = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemFive = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemSix = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        mOrder.addItem(mItemThree);
        mOrder.addItem(mItemFour);
        mOrder.addItem(mItemFive);
        mOrder.addItem(mItemSix);

        mOrder.removeItem(1, 3.00);
        mOrder.removeItem(1, 4.00);

        assertEquals(mOrder.getItems().size(),4);
    }

    @Test
    public void testTotalAfterRemove(){
        Order mOrder = new Order();
        OrderItem mItemOne = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemTwo = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemThree = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemFour = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        OrderItem mItemFive = new OrderItem("Large Latte", 2.50, 1, 3.00);
        mItemOne.addAddition("Vanilla Syrup", 0.5);

        OrderItem mItemSix = new OrderItem("Americano", 1.50, 2, 4.00);
        mItemOne.addAddition("Caramel Syrup", 0.5);

        mOrder.addItem(mItemOne);
        mOrder.addItem(mItemTwo);
        mOrder.addItem(mItemThree);
        mOrder.addItem(mItemFour);
        mOrder.addItem(mItemFive);
        mOrder.addItem(mItemSix);

        mOrder.removeItem(1, 3.00);
        mOrder.removeItem(1, 4.00);

        assertEquals(mOrder.getTotal(),14.00, 0);
    }

}
