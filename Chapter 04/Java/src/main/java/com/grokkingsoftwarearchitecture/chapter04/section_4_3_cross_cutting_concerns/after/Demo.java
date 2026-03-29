package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;

public class Demo {
    public static void run() {
        System.out.println("--- Running 'After' (Injected Logger) ---");
        
        // Dependencies are created and injected at the start
        Logger logger = new FileLogger();
        OrderService service = new OrderService(logger);
        
        service.saveOrder(new Order());
        System.out.println("-----------------------------------------");
    }
}