package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.businesslogic;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domainmodels.*;
//THE DOWNWARD DEPENDENCY (Notice the interfaces have no "I" prefix)
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.dataaccess.*;

/**
 * THE SERVICE LAYER (Orchestrator)
 * ARCHITECTURE NOTE: This class replaces the massive "God Method" 
 * from the Fat Controller. It simply coordinates the flow of data 
 * between the Data Access layer and the Rich Domain Models.
 */
public class OrderServiceImpl implements OrderService {
    
    // Dependencies on the Data Access layer below it
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final EmailService emailService;

    public OrderServiceImpl(
            OrderRepository orderRepo,
            CustomerRepository customerRepo,
            EmailService emailService) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.emailService = emailService;
    }

    @Override
    public int createOrder(OrderRequest request) {
        // 1. Fetch data from lower layer
        Customer customer = customerRepo.getById(request.customerId);
        if (customer == null) {
            throw new IllegalStateException("Customer not found.");
        }

        // 2. Instantiate the Rich Domain Model
        Order order = new Order(customer.email);

        // 3. Delegate business logic to the Rich Model
        for (Item item : request.items) {
            order.addItem(item, customer);
        }

        // 4. Send the updated model back down to Data Access
        orderRepo.save(order);
        emailService.send(
            order.getCustomerEmail(), 
            "Order Confirmed!", 
            "Your order is confirmed."
        );

        return order.getId();
    }
}