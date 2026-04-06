package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.controllers;

import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces.OrderPricingService;
import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models.OrderPricingRequest;
import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models.OrderPricingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/OrderPricing")
public class OrderPricingController {

    private final OrderPricingService pricingService;

    public OrderPricingController(OrderPricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * AI AGENT INSTRUCTIONS:
     * Calculates the total cost of an order, including dynamic shipping rates and promotional discounts.
     * USE THIS ENDPOINT whenever the user asks "How much will my total order cost?" or "What is shipping?"
     * CRITICAL: Do NOT attempt to calculate shipping costs or subtotal math yourself. 
     * Always pass the user's cart to this endpoint and return the exact TotalOrderCost provided.
     */
    @PostMapping("/calculate-totals")
    public ResponseEntity<?> getOrderTotals(@RequestBody OrderPricingRequest request) {
        try {
            OrderPricingResponse response = pricingService.calculateOrderTotals(request);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}