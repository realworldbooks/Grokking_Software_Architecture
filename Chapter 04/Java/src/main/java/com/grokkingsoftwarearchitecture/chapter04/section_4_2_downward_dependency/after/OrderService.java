package com.grokkingsoftwarearchitecture.chapter04;

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