package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        LogManager.info(Demo.class, "--- Running 'After' (Downward Dep) ---");
        
        // Composition Root: Wiring the dependencies
        OrderRepository afterRepo = new SqlOrderRepository();
        OrderService afterService = new OrderService(afterRepo);
        
        afterService.saveOrder(new Order());
        LogManager.info(Demo.class, "--------------------------------------");
    }
}