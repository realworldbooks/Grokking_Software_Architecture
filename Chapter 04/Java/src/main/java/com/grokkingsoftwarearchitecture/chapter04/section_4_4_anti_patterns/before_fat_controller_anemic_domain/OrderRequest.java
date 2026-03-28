package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    @Schema(defaultValue = "1")
    public int customerId = 1;
    
    public List<OrderItemRequest> items = new ArrayList<>(List.of(new OrderItemRequest()));
}