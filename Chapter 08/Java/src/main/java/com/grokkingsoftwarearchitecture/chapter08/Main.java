package com.grokkingsoftwarearchitecture.chapter08;

import java.util.Scanner;

import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.Demo;

/**
 * THE UI CONTROLLER (Separation of Concerns):
 * By moving the interactive menu into its own file, we keep our architecture clean.
 * This file handles the user experience, while Demo.java handles the database logic.
 * * Note: In Java, this entry point is conventionally named Main.java.
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n============================================================");
            System.out.println("=== Chapter 8: SQL vs. NoSQL vs. Vector ===");
            System.out.println("============================================================");
            System.out.println("0. The Literal Search (The Naive Baseline)");
            System.out.println("1. The Metadata Workaround (Columns & Tags)");
            System.out.println("2. The 'Fat Finger' Test (Fuzzy Intent)");
            System.out.println("3. The Schema Agility Test (Business Pivot)");
            System.out.println("4. The Aggregation Test (Give Me The Math)");
            System.out.println("5. The Hybrid Search (The Holy Grail)");
            System.out.println("6. Exit");
            System.out.println("============================================================");
            
            System.out.print("\nEnter your choice (0-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "0":
                    Demo.runScenario0LiteralSearch();
                    break;
                case "1":
                    Demo.runScenario1MetadataWorkaround();
                    break;
                case "2":
                    Demo.runScenario2FatFinger();
                    break;
                case "3":
                    Demo.runScenario3SchemaAgility();
                    break;
                case "4":
                    Demo.runScenario4Aggregation();
                    break;
                case "5":
                    Demo.runScenario5HybridSearch();
                    break;
                case "6":
                    System.out.println("Exiting Chapter 8 Demo...");
                    scanner.close();
                    return; // Exits the while loop and the application
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                    continue;
            }
            
            System.out.println("\nPress Enter to return to the main menu...");
            scanner.nextLine();
        }
    }
}