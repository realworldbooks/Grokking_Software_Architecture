package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

/**
 * BUSINESS LOGIC LAYER.
 * ARCHITECTURE WARNING: This class is "welded" to the StaticFileLogger.
 * You cannot test SaveOrder without also executing the static
 * logger logic. This violates the Dependency Inversion Principle.
 */
public class OrderService {
    public void saveOrder(Order order) {
        // HIDDEN DEPENDENCY: This is not visible in the API.
        System.out.println("Saving order..." + order.getId());
        System.out.println("(BEFORE_SERVICE) Order saved.");
    }
}