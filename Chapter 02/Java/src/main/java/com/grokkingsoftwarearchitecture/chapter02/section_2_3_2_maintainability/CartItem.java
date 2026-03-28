package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability;

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