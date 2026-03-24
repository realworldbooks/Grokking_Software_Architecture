package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models;

/**
 * ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
 * how items are priced and discounted is encapsulated inside the 
 * Rich 'Order' model, not here.
 */
public class Item {
    public double price;
    public int quantity;
}