package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.before;

import java.util.List;

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