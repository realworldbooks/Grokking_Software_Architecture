package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation.controllers;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.businesslogic.*;

/**
 * THE THIN CONTROLLER
 * ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
 * Controller" anti-pattern.
 */
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public String createOrder(OrderRequest request) {
        int orderId = orderService.createOrder(request);
        return "{ \"OrderId\": " + orderId + " }";
    }
}