package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.business_logic;

/**
 * The Business Layer defines the contract for its own capabilities.
 * ARCHITECTURE NOTE: Idiomatic Java omits the "I" prefix for interfaces.
 */
public interface OrderService {
    int createOrder(OrderRequest request);
}