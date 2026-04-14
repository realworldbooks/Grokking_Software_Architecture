// src/main/java/com/grokking/Main.java
package com.grokkingsoftwarearchitecture.chapter08;

import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.Demo; 
import com.grokkingsoftwarearchitecture.chapter08.section_8_2_1_declarative_querying.Demo2;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n============================================================");
            System.out.println("=== Chapter 8: The Database as an Architectural Pillar ===");
            System.out.println("============================================================");
            
            System.out.println("\n--- Section 8.1.4: SQL vs. NoSQL vs. Vector ---");
            System.out.println("1. The Literal Search (The Naive Baseline)");
            System.out.println("2. The Metadata Workaround (Columns & Tags)");
            System.out.println("3. The 'Fat Finger' Test (Fuzzy Intent)");
            System.out.println("4. The Schema Agility Test (Business Pivot)");
            System.out.println("5. The Aggregation Test (Give Me The Math)");
            System.out.println("6. The Hybrid Search (The Holy Grail)");

            System.out.println("\n--- Section 8.2.1: Declarative Querying (ORMs) ---");
            System.out.println("7. Run Query Comparison (Raw SQL vs. Hibernate)");
            
            System.out.println("\n0. Exit");
            System.out.println("============================================================");
            
            System.out.print("\nEnter your choice (0-7): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": Demo.runScenario0LiteralSearch(); break;
                case "2": Demo.runScenario1MetadataWorkaround(); break;
                case "3": Demo.runScenario2FatFinger(); break;
                case "4": Demo.runScenario3SchemaAgility(); break;
                case "5": Demo.runScenario4Aggregation(); break;
                case "6": Demo.runScenario5HybridSearch(); break;
                
                case "7": Demo2.runQueryComparison(); break;
                
                case "0":
                    System.out.println("Exiting Chapter 8 Demo...");
                    // Need to shut down Hibernate's connection pool before exiting
                    com.grokkingsoftwarearchitecture.chapter08.section_8_2_1_declarative_querying.HibernateUtil.getSessionFactory().close();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 7.");
                    continue;
            }
            
            System.out.println("\nPress Enter to return to the main menu...");
            scanner.nextLine();
        }
    }
}