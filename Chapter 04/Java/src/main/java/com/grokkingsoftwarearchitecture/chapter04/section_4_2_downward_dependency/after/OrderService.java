package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

/**
 * BUSINESS LOGIC LAYER.
 * ARCHITECTURE NOTE: This service is "ignorant" of the database.
 * It only knows about the OrderRepository interface.
 */
public class OrderService {
    private final OrderRepository repo; 

    public OrderService(OrderRepository repo) { 
        this.repo = repo; 
    }

    public void saveOrder(Order order) {
        // Calls DOWNWARDS via interface 
        repo.save(order);
    }
}