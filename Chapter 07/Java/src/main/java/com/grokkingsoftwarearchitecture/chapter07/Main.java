package com.grokkingsoftwarearchitecture.chapter07;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        // ObjectMapper is Jackson's main tool for reading and parsing JSON
        ObjectMapper mapper = new ObjectMapper();
        
        // BULLETPROOF CHECK: Does the config file exist?
        File configFile = new File("Examples.json");

        if (!configFile.exists()) {
            System.out.println("[ERROR] Examples.json not found!");
            System.out.println("Make sure it is located in the root directory of your project.");
            return;
        }

        try {
            // Read the JSON file into a Map of ID strings to ExampleConfig objects
            Map<String, ExampleConfig> examples = mapper.readValue(
                    configFile,
                    new TypeReference<Map<String, ExampleConfig>>() {}
            );

            // Java HashMaps don't keep their order, so we use a TreeMap 
            // with a custom comparator to sort them numerically by their ID
            TreeMap<String, ExampleConfig> sortedExamples = new TreeMap<>(
                    (k1, k2) -> Integer.compare(Integer.parseInt(k1), Integer.parseInt(k2))
            );
            sortedExamples.putAll(examples);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Java doesn't have a reliable cross-platform Console.Clear(), 
                // so we print a few newlines to separate the menu iterations.
                System.out.println("\n\n=== Grokking Software Architecture Chapter 07: Java Examples ===\n");

                for (Map.Entry<String, ExampleConfig> entry : sortedExamples.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }

                System.out.println("\nType 'exit' to quit.");
                System.out.print("\nEnter your choice: ");
                String choice = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(choice)) {
                    break;
                }

                ExampleConfig selectedExample = examples.get(choice);

                if (selectedExample != null) {
                    System.out.println("\n--- Running " + selectedExample.getName() + " ---\n");

                    try {
                        // ARCHITECTURAL NOTE: Reflection finds the exact class string at runtime
                        Class<?> clazz = Class.forName(selectedExample.getType());
                        
                        Method runMethod = null;
                        
                        // Look for runAsync() first, then fallback to run()
                        try {
                            runMethod = clazz.getMethod("runAsync");
                        } catch (NoSuchMethodException e) {
                            runMethod = clazz.getMethod("run");
                        }
                        
                        // Execute the method! (We pass null because the method is static)
                        runMethod.invoke(null);
                        
                    } catch (ClassNotFoundException e) {
                        System.out.println("[ERROR] Could not find class: " + selectedExample.getType());
                        System.out.println("Check your package and class names in Examples.json.");
                    } catch (NoSuchMethodException e) {
                        System.out.println("[ERROR] Could not find a public static run() or runAsync() method on " + selectedExample.getType());
                    } catch (Exception e) {
                        System.out.println("[ERROR] An error occurred while running the example: " + e.getMessage());
                    }

                    System.out.println("\nPress Enter to return to the main menu...");
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }
            
            scanner.close();

        } catch (Exception e) {
            System.out.println("[ERROR] Could not parse Examples.json.");
            e.printStackTrace();
        }
    }
}

// This class perfectly maps to the JSON structure
class ExampleConfig {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}