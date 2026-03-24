package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

/**
 * ANTI-PATTERN: THE FAT CONTROLLER.
 * ARCHITECTURE WARNING: This class is doing way too much. 
 * It violates SRP and is nearly impossible to unit test.
 */
public class OrderController {

    public Response createOrder(OrderRequest request) {
        // 1. Validation Logic
        if (request.items == null || request.items.isEmpty()) {
            return Response.badRequest("Order must have items.");
        }

        // 2. Core Business Logic (Calculating Total)
        double total = 0;
        for (Item item : request.items) {
            total += item.price * item.quantity;
        }

        // 3. More Business Logic (Applying Discount)
        if ("Gold".equals(request.customerType)) {
            total *= 0.9; // 10% discount
        }

        // 4. Data Access Logic
        MyDbContext db = new MyDbContext();
        Order order = new Order();
        order.total = total;
        db.save(order);

        // 5. External Service Logic
        SmtpEmailService email = new SmtpEmailService();
        email.send(request.customerEmail, "Order Confirmed!");

        return Response.ok(order.id);
    }
}