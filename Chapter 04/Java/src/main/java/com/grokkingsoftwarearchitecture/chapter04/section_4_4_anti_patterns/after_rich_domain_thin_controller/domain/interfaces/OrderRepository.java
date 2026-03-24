package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models.Order;

/**
 * ARCHITECTURE NOTE: In a traditional N-Tier architecture, the 
 * Data Access Layer defines the contracts for accessing data. 
 * The Business Logic layer above will be forced to depend on 
 * this layer to use these interfaces.
 */
public interface OrderRepository {
    Order getById(int orderId);
    void save(Order order);
}