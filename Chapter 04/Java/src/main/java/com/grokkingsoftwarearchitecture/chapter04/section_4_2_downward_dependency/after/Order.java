package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

/**
 * A simple model for the example.
 */
public class Order {
    private int id;

    public Order() {
        // Default constructor
    }

    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
}