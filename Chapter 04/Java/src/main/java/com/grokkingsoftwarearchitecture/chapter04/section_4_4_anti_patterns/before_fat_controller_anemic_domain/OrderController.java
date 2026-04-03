package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/Order")
public class OrderController {

    private static final Random RANDOM = new Random();

    /**
     * ARCHITECTURAL NOTE: The "God Method" Transaction Script
     * Because this method instantiates its own dependencies (using the 'new' keyword), 
     * it is completely untestable in isolation.
     */
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest request) {
        
        // 1. Validation Logic
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Order must have items.");
        }

        // ARCHITECTURAL NOTE: Tight Coupling to Infrastructure
        MyDbContext dbContext = new MyDbContext();
        try {
            // ARCHITECTURAL NOTE: Messy Inline Lookup
            Customer customer = dbContext.customers.stream()
                .filter(c -> c.id == request.getCustomerId())
                .findFirst()
                .orElse(null);
                
            if (customer == null) {
                return ResponseEntity.badRequest().body("Customer not found.");
            }

            double total = 0;

            // ARCHITECTURAL NOTE: Leaked Data Access & Core Business Logic
            for (OrderItemRequest reqItem : request.getItems()) {
                DbItem dbItem = dbContext.items.stream()
                    .filter(i -> i.id == reqItem.itemId)
                    .findFirst()
                    .orElse(null);
                    
                if (dbItem == null) {
                    return ResponseEntity.badRequest().body("Item " + reqItem.itemId + " not found.");
                }

                total += dbItem.price * reqItem.quantity;
            }

            // ARCHITECTURAL NOTE: Hardcoded Business Rules
            if ("Gold".equals(customer.type)) {
                total *= 0.9; // 10% discount
            }

            // ARCHITECTURAL NOTE: The Anemic Domain Model Usage
            Order order = new Order();
            order.setId(RANDOM.nextInt(9000) + 1000);
            order.setTotal(total);
            order.setCustomerEmail(customer.email);

            dbContext.orders.add(order);
            dbContext.saveChanges();

            // ARCHITECTURAL NOTE: Hidden Side Effects
            SmtpEmailService emailService = new SmtpEmailService();
            emailService.send(order.getCustomerEmail(), "Order Confirmed!");

            return ResponseEntity.ok(Map.of(
                "orderId", order.getId(),
                "totalPrice", order.getTotal(),
                "customerEmail", order.getCustomerEmail()
            ));
            
        } finally {
            dbContext.close();
        }
    }
}