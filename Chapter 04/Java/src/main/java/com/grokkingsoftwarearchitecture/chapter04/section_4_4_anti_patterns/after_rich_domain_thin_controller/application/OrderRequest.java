package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.application;

import java.util.List;

/**
 * DTO (Data Transfer Object) for incoming requests.
 * ARCHITECTURE NOTE: We use a specific Request DTO rather than the 
 * Domain to define our API contract. This prevents "Over-posting" 
 * attacks where a user might try to send a fake price in the JSON.
 */
public class OrderRequest {
    private int customerId;
    private List<OrderItemRequest> items;

    public OrderRequest() {
        // Default constructor for deserialization
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public static class OrderItemRequest {
        private int itemId;
        private int quantity;

        public OrderItemRequest() {
            // Default constructor for deserialization
        }

        public int getItemId() { return itemId; }
        public void setItemId(int itemId) { this.itemId = itemId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}