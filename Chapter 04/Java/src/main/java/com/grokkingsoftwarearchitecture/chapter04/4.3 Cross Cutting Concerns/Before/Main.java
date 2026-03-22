package com.grokkingsoftwarearchitecture.chapter04;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Running 'Before' (Static Logger) ---");
        
        OrderService beforeService = new OrderService();
        beforeService.saveOrder(new Order());
        
        System.out.println("-----------------------------------------");
    }
}