package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;


/**
 * Insfrastructure Layer
 * * ARCHITECTURE NOTE:
 * This concrete implementation sits in the Infrastructure layer 
 * and implements the interface defined by the domain.
 */
public class SqlOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("(After Refactor) Saving order to SQL...");
    }
}