package com.grokking.chapter09;

import com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.Demo;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n============================================================");
            System.out.println("=== Chapter 9: Cloud Native & Stateless Architecture ===");
            System.out.println("============================================================");
            
            System.out.println("\n--- Section 9.2.3: Stateful vs. Stateless Design ---");
            System.out.println("1. Run Stateful Scenario (The Fragile Monolith)");
            System.out.println("2. Run Stateless Scenario (Cloud Native S3)");
            
            System.out.println("\n0. Exit");
            System.out.println("============================================================");
            
            System.out.print("\nEnter your choice (0-2): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": 
                    Demo.runStatefulScenario(); 
                    break;
                case "2": 
                    Demo.runStatelessScenario(); 
                    break;
                case "0":
                    System.out.println("Exiting Chapter 9 Demo...");
                    scanner.close();
                    return; 
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    continue;
            }
            
            System.out.println("\nPress Enter to return to the main menu...");
            scanner.nextLine();
        }
    }
}