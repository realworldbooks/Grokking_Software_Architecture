package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models;

import io.swagger.v3.oas.annotations.media.Schema;

public class CartItem {
    /**
     * The unique ID of the product. 
     * VALID VALUES: 'WIDGET-99', 'WIDGET-HEAVY', 'DIGITAL-EBOOK-01'.
     * Do not send any other values.
     * * Example: WIDGET-99
     */
    @Schema(description = "The unique ID of the product.", example = "WIDGET-99")
    private String productId;

    /**
     * How many of this item the user is buying. Must be 1 or greater.
     * * Example: 1
     */
    @Schema(description = "Quantity of the item.", example = "2")
    private int quantity;

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}