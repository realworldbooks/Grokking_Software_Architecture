package com.grokkingsoftwarearchitecture.chapter04;

/**
 * INFRASTRUCTURE LAYER: DATABASE.
 * ARCHITECTURE NOTE: In the 'Before' state, the Controller 
 * manages the lifecycle of this class (New is Glue).
 */
public class MyDbContext {
    public void save(Order order) {
        System.out.println("  [DB] Persistence logic: Saving Order " 
            + order.id + " to SQL.");
    }
}