package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

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