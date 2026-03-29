package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

public class Demo {
    public static void run() {
        System.out.println("--- Running 'Before' (Static Logger) ---");
        
        OrderService beforeService = new OrderService();
        beforeService.saveOrder(new Order());
        
        System.out.println("-----------------------------------------");
    }
}