package aiapi.demo.models;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * THE DTO (The AI's Input Form).
 * Extracted to ensure proper Separation of Concerns.
 */
public class ShippingRequest {
    
    @Schema(description = "The unique ID of the physical product. Do NOT send digital product IDs (like MP3s or eBooks).", required = true)
    public String productId;

    @Schema(description = "The destination zip code. Must be exactly 5 digits.", required = true)
    public String zipCode;
}