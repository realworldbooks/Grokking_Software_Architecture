package com.grokkingsoftwarearchitecture.chapter06;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        try {
            File configFile = new File("Examples.json");
            if (!configFile.exists()) {
                System.err.println("[ERROR] Examples.json not found in root directory.");
                return;
            }

            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode examples = rootNode.get("examples");

            while (true) {
                
                System.out.println("\n=== " + rootNode.get("title").asText() + " ===");
                
                for (JsonNode example : examples) {
                    System.out.println(example.get("id").asText() + ". " + example.get("title").asText());
                }
                
                System.out.print("\nType 'exit' to quit or enter your choice: ");
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("exit")) break;

                runExample(examples, choice, args);
            }
        } catch (Exception e) {
            System.err.println("[CRITICAL ERROR] " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void runExample(JsonNode examples, String choice, String[] args) {
        for (JsonNode example : examples) {
            if (example.get("id").asText().equals(choice)) {
                String className = example.get("targetClass").asText();
                boolean isSpringBoot = example.has("isSpringBoot") && example.get("isSpringBoot").asBoolean();

                try {
                    Class<?> clazz = Class.forName(className);
                    if (isSpringBoot) {
                        // Spring Boot handle its own logging/startup
                        SpringApplication.run(clazz, args);
                    } else {
                        // Standard Java execution for REST/GraphQL demos
                        Method runMethod = clazz.getMethod("run");
                        runMethod.invoke(null);
                        
                        System.out.println("\nPress ENTER to return to the main menu...");
                        new Scanner(System.in).nextLine();
                    }
                } catch (Exception e) {
                    System.err.println("[LAUNCH ERROR] Could not run " + className + ": " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Invalid choice. Please try again.");
    }
}