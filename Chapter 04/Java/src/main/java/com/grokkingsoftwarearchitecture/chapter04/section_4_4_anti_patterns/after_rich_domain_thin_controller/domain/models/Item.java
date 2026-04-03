package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models;

/**
 * ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
 * how items are priced and discounted is encapsulated inside the 
 * Rich 'Order' model, not here.
 */
public class Item {
    private int id;
    private double price;
    private int quantity;

    public Item() {
        // Default constructor
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}