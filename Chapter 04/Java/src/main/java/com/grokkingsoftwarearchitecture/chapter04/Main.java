package com.grokkingsoftwarearchitecture.chapter04;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            // Load the menu configuration
            rootNode = mapper.readTree(new File("Examples.json"));
        } catch (Exception e) {
            System.out.println("[ERROR] Could not load Examples.json. Make sure it is in the project root.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        JsonNode examples = rootNode.get("examples");

        while (true) {
            System.out.println("\n=== " + rootNode.get("title").asText() + " ===");
            
            for (JsonNode example : examples) {
                System.out.println(example.get("id").asText() + ". " + example.get("title").asText());
            }
            
            System.out.println("Type 'exit' to quit.");
            System.out.print("\nEnter your choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("exit")) {
                break;
            }

            String targetClass = getTargetClassName(examples, choice);

            if (targetClass != null) {
                runDemo(targetClass);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static String getTargetClassName(JsonNode examples, String choice) {
        for (JsonNode example : examples) {
            if (example.get("id").asText().equals(choice)) {
                return example.get("targetClass").asText();
            }
        }
        return null; // Return null if the user enters a number not in the JSON
    }

    private static void runDemo(String className) {
        try {
            System.out.print("\033[H\033[2J"); 
            System.out.flush();

            Class<?> clazz = Class.forName(className);
            Method runMethod = clazz.getMethod("run");
            runMethod.invoke(null);
            
            System.out.println("\nPress ENTER to return to the main menu...");
            new Scanner(System.in).nextLine();

        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] Could not find class: " + className);
            System.out.println("Make sure your Demo.java files are compiled and the package names match exactly.");
        } catch (NoSuchMethodException e) {
            System.out.println("[ERROR] The class " + className + " does not have a 'public static void run()' method.");
        } catch (Exception e) {
            System.out.println("[RUNTIME ERROR] " + e.getCause());
        }
    }
}