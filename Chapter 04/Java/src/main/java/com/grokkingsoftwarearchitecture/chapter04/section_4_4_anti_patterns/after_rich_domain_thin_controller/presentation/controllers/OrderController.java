package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation.controllers;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.application.*;
/**
 * THE THIN CONTROLLER
 * ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
 * Controller" anti-pattern. Its ONLY job is to translate an HTTP POST 
 * request into a Business Logic call and return a response.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the order to be created.",
            required = true,
            content = @Content(
                schema = @Schema(implementation = OrderRequest.class),
                examples = @ExampleObject(
                    name = "Sample Order",
                    summary = "A sample order with two items",
                    value = "{\n" +
                            "  \"customerId\": 123,\n" +
                            "  \"items\": [\n" +
                            "    {\n" +
                            "      \"itemId\": 1,\n" +
                            "      \"quantity\": 1\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"itemId\": 2,\n" +
                            "      \"quantity\": 2\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}"
                )
            )
        )
        @RequestBody OrderRequest request) {
        try {
            // The service returns the rich OrderResponse DTO
            OrderResponse response = orderService.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Matches the C# BadRequest(ex.Message) logic
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}