
package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;
import io.swagger.v3.oas.annotations.media.Schema;

public class OrderItemRequest {
    @Schema(defaultValue = "1")
    public int itemId = 1;
    
    @Schema(defaultValue = "3")
    public int quantity = 3;
} 
