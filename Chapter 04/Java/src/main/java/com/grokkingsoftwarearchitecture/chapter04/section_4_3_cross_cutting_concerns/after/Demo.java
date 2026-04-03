package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        LogManager.info(Demo.class, "--- Running 'After' (Injected Logger) ---");
        
        // Dependencies are created and injected at the start
        Logger logger = new FileLogger();
        OrderService service = new OrderService(logger);
        
        service.saveOrder(new Order());
        LogManager.info(Demo.class, "-----------------------------------------");
    }
}