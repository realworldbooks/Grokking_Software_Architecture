package com.grokkingsoftwarearchitecture.chapter04.section_4_3_downward_dependency.after;

public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        System.out.println("--- Running 'After Refactoring' (Downward Dependency) ---");
        
        // Composition Root: Wiring the dependencies
        OrderRepository afterRepo = new SqlOrderRepository();
        OrderService afterService = new OrderService(afterRepo);
        
        afterService.saveOrder(new Order());
        System.out.println("----------------------------------------------");
    }
}