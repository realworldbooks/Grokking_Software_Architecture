package aiapi.demo.controllers;

import aiapi.demo.models.ShippingRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    @PostMapping("/calculate-shipping")
    @Operation(
        summary = "Calculates the shipping cost for a specific physical item.",
        description = "USE THIS ENDPOINT whenever the user asks 'How much is shipping?'. Do NOT use this endpoint for digital items."
    )
    public ResponseEntity<Double> getShipping(@RequestBody ShippingRequest request) {
        
        if (request.productId != null && request.productId.startsWith("DIGITAL")) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(5.99);
    }
}