package com.barry.ntufood;
import java.util.HashMap;
import java.util.Map;


public class OrderItem {
    private String itemName = "";
    private double price = 0.00;
    private int amount = 0;
    private HashMap<String, Double> additions = new HashMap<String, Double>();
    private Double total = 0.00;

    public OrderItem() {}

    public OrderItem(String itemName, double price, int amount, double total) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.total = total;
    }

    public OrderItem(String itemName, double price, int amount, HashMap<String, Double> additions, double total) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.additions = additions;
        this.total = total;
    }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public HashMap<String, Double> getAdditions() {
        return additions;
    }

    public void setAdditions(HashMap<String, Double> additions) {
        this.additions = additions;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void addAddition(String name, Double price){
        additions.put(name, price);
    }
}
