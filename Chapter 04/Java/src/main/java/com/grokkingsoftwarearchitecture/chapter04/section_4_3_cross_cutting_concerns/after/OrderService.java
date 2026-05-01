package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;


/**
 * 2. THE CLASS "ASKS" FOR THE DEPENDENCY.
 * ARCHITECTURE NOTE: By injecting the Logger via the constructor,
 * we follow DIP. The service is now "logger-agnostic."
 */
public class OrderService {
    private final Logger logger;

    public OrderService(Logger logger) {
        this.logger = logger;
    }

    public void saveOrder(Order order) {
        // 3. Use the abstraction
        logger.log("Saving order... " + order.getId());
        System.out.println("(AFTER_SERVICE) Order saved.");
    }
}