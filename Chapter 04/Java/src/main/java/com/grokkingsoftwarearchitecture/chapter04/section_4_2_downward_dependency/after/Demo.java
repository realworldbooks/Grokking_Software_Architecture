package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

public class Demo {
    public static void run() {
        System.out.println("--- Running 'After' (Downward Dep) ---");
        
        // Composition Root: Wiring the dependencies
        OrderRepository afterRepo = new SqlOrderRepository();
        OrderService afterService = new OrderService(afterRepo);
        
        afterService.saveOrder(new Order());
        System.out.println("--------------------------------------");
    }
}