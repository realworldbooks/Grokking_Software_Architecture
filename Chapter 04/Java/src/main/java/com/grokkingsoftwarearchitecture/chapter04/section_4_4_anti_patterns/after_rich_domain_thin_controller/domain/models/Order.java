package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * THE RICH DOMAIN
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
    private Customer customer;
    
    // Encapsulation: Prevents external code from doing items.add() 
    // which would bypass our recalculateTotal logic.
    private final List<Item> items = new ArrayList<>();

    /**
     * ARCHITECTURE NOTE: By injecting the full Customer entity instead of just 
     * an email string, the Order gains the "context" needed to calculate 
     * its own TotalPrice.
     */
    public Order(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
        this.customer = customer;
        this.id = new Random().nextInt(9000) + 1000;
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }

    /** Encapsulation: Prevents external code from bypassing our business rules. */
    public List<Item> getItems() { return Collections.unmodifiableList(items); }

    public boolean isEligibleForDiscount() {
        return customer != null && "Gold".equals(customer.getType());
    }

    /**
     * ARCHITECTURE NOTE: We use an alias here to reflect the customer's current email.
     * If the business required a 'snapshot', we would store this as a separate string.
     */
    public String getCustomerEmail() { return customer.getEmail(); }

    /**
     * THE ATOMIC TRUTH: Logic and data are now perfectly unified.
     * This replaces the manual 'RecalculateTotal' method.
     */
    public double getTotalPrice() {
        double sum = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        return isEligibleForDiscount() ? sum * GOLD_DISCOUNT_RATE : sum;
    }

    /**
     * Behavior is now co-located with the data it mutates.
     */
    public void addItem(Item item) {
        // Business Rule: Prices must be positive
        if (item.getPrice() <= 0) {
            throw new IllegalStateException(
                "Item price must be positive.");
        }   
        items.add(item);
    }
}