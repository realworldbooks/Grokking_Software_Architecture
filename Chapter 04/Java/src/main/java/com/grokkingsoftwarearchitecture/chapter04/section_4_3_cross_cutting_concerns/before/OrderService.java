package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * BUSINESS LOGIC LAYER.
 * ARCHITECTURE WARNING: This class is "welded" to the StaticFileLogger.
 * You cannot test SaveOrder without also executing the static
 * logger logic. This violates the Dependency Inversion Principle.
 */
public class OrderService {
    public void saveOrder(Order order) {
        // HIDDEN DEPENDENCY: This is not visible in the API.
        StaticFileLogger.log("Saving order..." + order.getId());
        LogManager.info(OrderService.class, "(BEFORE_SERVICE) Order saved.");
    }
}