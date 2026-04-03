package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

import java.util.Random;

/**
 * DATA TRANSFER OBJECT (DTO).
 */
public class Order {
    private static final Random RANDOM = new Random();

    private int id = RANDOM.nextInt(9000) + 1000; // generate a random 4-digit order ID

    public Order() {
        // Default constructor
    }

    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
}