package com.aiapi.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    // 1. THE CONTROLLER (The AI's Tool)
    @PostMapping("/calculate-shipping")
    @Operation(
        summary = "Calculates physical shipping cost",
        description = "USE THIS ENDPOINT whenever the user asks 'How much is shipping?' Do NOT use for digital items."
    )
    public ResponseEntity<?> getShipping(@RequestBody ShippingRequest request) {
        if (request.getProductId().startsWith("DIGITAL")) {
            return ResponseEntity.badRequest().body("Digital items do not require shipping.");
        }
        return ResponseEntity.ok(5.99);
    }
}

// 2. THE DTO (The AI's Input Form)
class ShippingRequest {
    @Schema(description = "The unique ID of the physical product. Do NOT send digital product IDs.")
    private String productId;

    @Schema(description = "The destination zip code. Must be exactly 5 digits.")
    private String zipCode;

    // Getters and setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}
