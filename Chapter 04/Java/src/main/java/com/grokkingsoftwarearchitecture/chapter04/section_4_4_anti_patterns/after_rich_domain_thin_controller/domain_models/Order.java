package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain_models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * THE RICH DOMAIN MODEL
 * ARCHITECTURE NOTE: This solves the "Anemic Domain" anti-pattern.
 * In the "Before" state, the Controller calculated the total and
 * applied discounts. Now, the Order class is responsible for its 
 * own data integrity. 
 */
public class Order {
    private static final double GOLD_DISCOUNT_RATE = 0.9;
    
    // Encapsulation: External classes cannot arbitrarily change 
    // the total or the id. They must use the provided methods.
    private int id;
    private double total;
    private String customerEmail;
    
    // Encapsulation: Prevents external code from doing items.add() 
    // which would bypass our recalculateTotal logic.
    private final List<Item> items = new ArrayList<>();

    public Order(String customerEmail) {
        if (customerEmail == null || customerEmail.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        this.customerEmail = customerEmail;
        this.id = new Random().nextInt(9000) + 1000; // Simulated ID
    }

    public int getId() { return id; }
    public double getTotal() { return total; }
    public String getCustomerEmail() { return customerEmail; }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Behavior is now co-located with the data it mutates.
     */
    public void addItem(Item item, Customer customer) {
        // Business Rule: Prices must be positive
        if (item.price <= 0) {
            throw new IllegalStateException(
                "Item price must be positive.");
        }
        
        items.add(item);
        recalculateTotal(customer);
    }

    /**
     * The discount logic lives here! If another part of the system 
     * creates an Order, they get this logic automatically. No more 
     * duplicated logic scattered across multiple controllers.
     */
    private void recalculateTotal(Customer customer) {
        System.out.println("(DOMAIN) Calculating total...");
        double sum = items.stream()
                          .mapToDouble(i -> i.price * i.quantity)
                          .sum();
                          
        if ("Gold".equals(customer.type)) {
            System.out.println("(DOMAIN) Applying Gold discount.");
            sum *= GOLD_DISCOUNT_RATE; // 10% discount logic
        }
        this.total = sum;
    }
}