package com.barry.ntufood;

import android.util.Log;


import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String userID = "";
    private String outlet = "";
    private String table = "";
    private Timestamp datetime;
    private double total = 0.00;
    private boolean isComplete = false;
    private List<OrderItem> items = new ArrayList<OrderItem>();

    public Order() {
    }

    public Order(String userID, String outlet, String table, double total) {
        this.userID = userID;
        this.outlet = outlet;
        this.table = table;
        this.total = total;
    }

    public Order(String userID, String outlet, String table, double total, List<OrderItem> items) {
        this.userID = userID;
        this.outlet = outlet;
        this.table = table;
        this.total = total;
        this.items = items;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item){
        this.items.add(item);
        this.total += item.getTotal();
    }

    public void removeItem(int item, double price){
        this.total -= price;
        this.items.remove(item);
    }

    public String timestampFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY HH:mm:ss");
        return dateFormat.format(this.datetime.toDate());
    }
}
