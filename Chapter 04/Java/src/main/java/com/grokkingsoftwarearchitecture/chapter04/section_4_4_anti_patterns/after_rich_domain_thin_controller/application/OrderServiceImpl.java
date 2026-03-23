package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.application;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.*;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure.*;

/**
 * THE SERVICE LAYER (Orchestrator)
 * ARCHITECTURE NOTE: This class replaces the massive "God Method" 
 * from the Fat Controller. It doesn't write to the DB, nor does 
 * it calculate math. It simply coordinates the flow of data 
 * between the Data Access layer and the Rich Domain Models.
 */
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ItemRepository itemRepo; // New dependency for secure lookups
    private final EmailService emailService;

    public OrderServiceImpl(
            OrderRepository orderRepo, 
            CustomerRepository customerRepo,
            ItemRepository itemRepo,
            EmailService emailService) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
        this.emailService = emailService;
    }

    @Override
    public int createOrder(OrderRequest request) {
        // 1. Fetch data from lower layer
        Customer customer = customerRepo.getById(request.customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found.");
        }

        // 2. Instantiate the Rich Domain
        Order order = new Order(customer.email);

        // 3. Delegate business logic to the Rich Model
        for (OrderRequest.OrderItemRequest itemReq : request.items) {
            // SECURITY LOOKUP: Fetch the actual Item from the DB to get the valid Price
            // This ensures the system uses the official price, not the one from the request.
            Item actualItem = itemRepo.getById(itemReq.itemId);
            if (actualItem == null) {
                throw new RuntimeException("Item " + itemReq.itemId + " not found.");
            }

            // Set the quantity from the user's request onto the domain object
            actualItem.quantity = itemReq.quantity;

            // The service doesn't care about discount rules; 
            // the Order model handles that internally.
            order.addItem(actualItem, customer);
        }

        // 4. Send the updated model back down to Infrastructure
        orderRepo.save(order);
        emailService.send(
            order.getCustomerEmail(), 
            "Confirmed!", 
            "Your order has been placed."
        );

        return order.getId();
    }
}