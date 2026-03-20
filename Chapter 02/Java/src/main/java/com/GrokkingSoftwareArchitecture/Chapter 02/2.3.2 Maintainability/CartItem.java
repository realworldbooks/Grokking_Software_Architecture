package com.architecturebook.chapter2;

public class CartItem {
    private String name;
    private double price;

    public CartItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}