package com.aiapi.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class ShippingRequest {
    @Schema(description = "The unique ID of the physical product. Do NOT send digital product IDs.")
    private String productId;

    @Schema(description = "The destination zip code. Must be exactly 5 digits.")
    private String zipCode;

    // Standard Getters and Setters omitted for brevity
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
}
