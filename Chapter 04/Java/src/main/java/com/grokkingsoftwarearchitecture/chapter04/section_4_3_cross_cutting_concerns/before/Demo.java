package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        LogManager.info(Demo.class, "--- Running 'Before' (Static Logger) ---");
        
        OrderService beforeService = new OrderService();
        beforeService.saveOrder(new Order());
        
        LogManager.info(Demo.class, "-----------------------------------------");
    }
}