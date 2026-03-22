package com.grokkingsoftwarearchitecture.chapter02.maintainability;

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