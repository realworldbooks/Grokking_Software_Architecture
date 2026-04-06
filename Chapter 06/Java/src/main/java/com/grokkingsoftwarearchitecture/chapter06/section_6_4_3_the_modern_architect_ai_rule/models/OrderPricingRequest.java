package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The payload required to price a user's cart.
 */
public class OrderPricingRequest {
    /**
     * The list of items in the user's shopping cart. 
     * Extract these from the user's conversation history.
     * * Example:
     * [
     * {
     * "productId": "WIDGET-99",
     * "quantity": 2
     * },
     * {
     * "productId": "DIGITAL-EBOOK-01",
     * "quantity": 1
     * }
     * ]
     */
    @Schema(description = "The list of items in the user's shopping cart.", 
            example = "[{\"productId\": \"WIDGET-99\", \"quantity\": 2}, {\"productId\": \"DIGITAL-EBOOK-01\", \"quantity\": 1}]")
    private List<CartItem> items;

    /**
     * The destination zip code. 
     * AI INSTRUCTION: Must be exactly 5 digits. If the user provided a 9-digit zip, strip the last 4.
     * * Example: 12211
     */
    @Schema(description = "The destination zip code.", example = "12211")
    private String zipCode;

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}