package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.before;

import java.util.List;

/**
 * A Simple Data Transfer Object (DTO).
 * * ARCHITECTURE NOTE: This class is actually fine! It contains no logic, just state. 
 * The problem isn't the data; the problem is how the data is processed.
 */
public class Order {
    public List<String> items;
    public double total;
    public String customerEmail;

    public Order(List<String> items, double total, String customerEmail) {
        this.items = items;
        this.total = total;
        this.customerEmail = customerEmail;
    }
}