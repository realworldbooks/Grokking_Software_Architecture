package com.grokkingsoftwarearchitecture.chapter04;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Running 'After' (Injected Logger) ---");
        
        // Dependencies are created and injected at the start
        Logger logger = new FileLogger();
        OrderService service = new OrderService(logger);
        
        service.saveOrder(new Order());
        System.out.println("-----------------------------------------");
    }
}