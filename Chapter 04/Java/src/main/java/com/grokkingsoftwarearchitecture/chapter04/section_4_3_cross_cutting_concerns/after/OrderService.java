package com.grokkingsoftwarearchitecture.chapter04;

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
        logger.log("Saving order...");
        System.out.println("(AFTER_SERVICE) Order saved.");
    }
}