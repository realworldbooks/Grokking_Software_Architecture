package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation.controllers;

import org.springframework.web.bind.annotation.*;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.application.*;

/**
 * THE THIN CONTROLLER
 * ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
 * Controller" anti-pattern. Its ONLY job is to translate an HTTP POST 
 * request into a Business Logic call and return a response.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        // Controller simply delegates work to the layer below it
        int orderId = orderService.createOrder(request);
        
        // Controller formats the HTTP response
        return new OrderResponse(orderId);
    }
}

// Simple record for the JSON response body { "orderId": 123 }
record OrderResponse(int orderId) {}