package com.grokkingsoftwarearchitecture.chapter04;

import java.util.List;

/**
 * THE ANEMIC DOMAIN MODEL.
 * ARCHITECTURE WARNING: This is just a data container #A.
 * It has no business logic, making it "Anemic" #B.
 */
public class Order {
    public int id;
    public double total;
    public String customerEmail;
    public List<Item> items;
}