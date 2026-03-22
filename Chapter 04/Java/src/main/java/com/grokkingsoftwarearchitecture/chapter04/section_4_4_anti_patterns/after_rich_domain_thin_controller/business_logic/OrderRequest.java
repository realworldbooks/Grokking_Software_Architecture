package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.business_logic;

import java.util.List;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain_models.Item;

/**
 * DTO (Data Transfer Object) for incoming requests.
 * ARCHITECTURE NOTE: We use a specific Request DTO rather than the 
 * Domain Model to define our API contract. This prevents "Over-posting" 
 * attacks where a user might try to send a fake price in the JSON.
 */
public class OrderRequest {
    public int customerId;
    public List<OrderItemRequest> items;

    public static class OrderItemRequest {
        public int itemId;
        public int quantity;
    }
}