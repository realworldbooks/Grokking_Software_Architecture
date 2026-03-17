package com.aiapi.demo.controllers;

import com.aiapi.demo.dtos.ShippingRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

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
