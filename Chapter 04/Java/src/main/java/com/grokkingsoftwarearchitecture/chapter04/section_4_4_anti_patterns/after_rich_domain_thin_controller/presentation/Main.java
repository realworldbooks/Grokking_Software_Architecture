package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.businesslogic.*;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.dataaccess.*;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domainmodels.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        
        // --- THE COMPOSITION ROOT ---
        // ARCHITECTURE NOTE: Because the Presentation layer sits at 
        // the very top of the stack, it is responsible for wiring 
        // all the layers together via Dependency Injection.
        
        // 1. Instantiate the Data Access Layer (Infrastructure)
        OrderRepository orderRepo = new SqlOrderRepository();
        CustomerRepository customerRepo = new SqlCustomerRepository();
        EmailService emailService = new SmtpEmailService();

        // 2. Inject Data Access into the Business Logic Layer
        OrderService orderService = new OrderServiceImpl(
            orderRepo, customerRepo, emailService
        );

        // 3. Inject Business Logic into the Presentation Layer
        OrderController app = new OrderController(orderService);

        // Print startup messages matching the C# Program.cs
        System.out.println("--- Running Traditional 4-Layer ---");
        System.out.println("Fat Controller and Anemic Domain eliminated.");

        // --- Simulate an incoming HTTP request ---
        OrderRequest request = new OrderRequest();
        request.customerId = 123;
        
        Item item1 = new Item();
        item1.price = 100.0;
        item1.quantity = 1;
        
        Item item2 = new Item();
        item2.price = 50.0;
        item2.quantity = 2;
        
        request.items = Arrays.asList(item1, item2);

        // Execute the controller endpoint
        String response = app.createOrder(request);
        System.out.println("HTTP 200 OK: " + response);
    }
}