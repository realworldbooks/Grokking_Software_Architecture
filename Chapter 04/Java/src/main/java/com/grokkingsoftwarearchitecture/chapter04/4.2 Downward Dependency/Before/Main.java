package com.grokkingsoftwarearchitecture.chapter04;

/**
 * The entry point for the Java application.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Running 'Before' (Upward Dep) ---");
        
        SomeRepository beforeRepo = new SomeRepository();
        beforeRepo.updateData(123, "New Data");
        
        System.out.println("------------------------------------");
    }
}