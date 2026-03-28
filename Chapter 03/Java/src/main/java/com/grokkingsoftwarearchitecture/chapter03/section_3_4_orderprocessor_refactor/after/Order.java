package com.grokkingsoftwarearchitecture.chapter03.section_3_4_orderprocessor_refactor.after;

import java.util.List;

/**
 * A Simple Data Transfer Object (DTO).
 * * ARCHITECTURE NOTE: Just like in the "Before" state, this remains a pure 
 * data container. It holds the state that our new, highly-cohesive services 
 * will operate on.
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