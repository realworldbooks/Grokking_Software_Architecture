package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.data_access;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain_models.Order;

/**
 * ARCHITECTURE NOTE: By isolating SQL logic here, we prevent 
 * database concerns from "leaking" into the Presentation or 
 * Business layers.
 */
// Concrete implementation for a SQL database (simulated)
public class SqlOrderRepository implements OrderRepository {
    @Override
    public Order getById(int orderId) { return null; }
    
    @Override
    public void save(Order order) { /* SQL Logic */ }
}