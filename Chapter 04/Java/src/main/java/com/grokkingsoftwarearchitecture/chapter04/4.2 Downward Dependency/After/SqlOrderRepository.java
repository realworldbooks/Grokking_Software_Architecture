package com.grokkingsoftwarearchitecture.chapter04;

/**
 * DATA ACCESS LAYER.
 * Implements the interface.
 */
public class SqlOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("(After Refactor) Saving order to SQL...");
    }
}