package com.grokkingsoftwarearchitecture.chapter04;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Running 'After' (Downward Dep) ---");
        
        // Composition Root: Wiring the dependencies
        OrderRepository afterRepo = new SqlOrderRepository();
        OrderService afterService = new OrderService(afterRepo);
        
        afterService.saveOrder(new Order());
        System.out.println("--------------------------------------");
    }
}