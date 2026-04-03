package com.grokkingsoftwarearchitecture.chapter04;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;
import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            // Load the menu configuration
            rootNode = mapper.readTree(new File("Examples.json"));
        } catch (Exception e) {
            LogManager.info(Main.class, "[ERROR] Could not load Examples.json. Make sure it is in the project root.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        JsonNode examples = rootNode.get("examples");

        while (true) {
            LogManager.info(Main.class, "\n=== {0} ===", rootNode.get("title").asText());
            
            for (JsonNode example : examples) {
                LogManager.info(Main.class, "{0}. {1}", example.get("id").asText(), example.get("title").asText());
            }
            
            LogManager.info(Main.class, "Type 'exit' to quit.");
            LogManager.info(Main.class, "\nEnter your choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("exit")) {
                break;
            }

            String targetClass = getTargetClassName(examples, choice);

            if (targetClass != null) {
                runDemo(targetClass);
            } else {
                LogManager.info(Main.class, "Invalid choice. Please try again.");
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
            Class<?> clazz = Class.forName(className);
            Method runMethod = clazz.getMethod("run");
            runMethod.invoke(null);
            
            LogManager.info(Main.class, "\nPress ENTER to return to the main menu...");
            @SuppressWarnings("resource")
            Scanner pauseScanner = new Scanner(System.in);
            pauseScanner.nextLine();

        } catch (ClassNotFoundException e) {
            LogManager.info(Main.class, "[ERROR] Could not find class: {0}", className);
            LogManager.info(Main.class, "Make sure your Demo.java files are compiled and the package names match exactly.");
        } catch (NoSuchMethodException e) {
            LogManager.info(Main.class, "[ERROR] The class {0} does not have a 'public static void run()' method.", className);
        } catch (Exception e) {
            LogManager.info(Main.class, "[RUNTIME ERROR] {0}", e.getCause());
        }
    }
}