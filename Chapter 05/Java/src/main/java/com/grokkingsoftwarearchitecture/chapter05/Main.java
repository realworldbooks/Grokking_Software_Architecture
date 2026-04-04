
package com.grokkingsoftwarearchitecture.chapter05;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Add this to the start of main() to clean up the output
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n");

        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File("Examples.json");

        if (!configFile.exists()) {
            LOGGER.severe("[ERROR] Examples.json not found!");
            LOGGER.severe("Make sure it is located in the root directory of your project.");
            return;
        }

        try {
            Map<String, ExampleConfig> examples = mapper.readValue(
                    configFile,
                    new TypeReference<Map<String, ExampleConfig>>() {}
            );

            TreeMap<String, ExampleConfig> sortedExamples = new TreeMap<>(
                    (k1, k2) -> Integer.compare(Integer.parseInt(k1), Integer.parseInt(k2))
            );
            sortedExamples.putAll(examples);

            runInteractiveMenu(sortedExamples, examples);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[ERROR] Could not parse Examples.json.", e);
        }
    }

    private static void runInteractiveMenu(TreeMap<String, ExampleConfig> sortedExamples, Map<String, ExampleConfig> examples) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            LOGGER.info("\n\n=== Grokking Software Architecture Chapter 05: Java Examples ===\n");

            for (Map.Entry<String, ExampleConfig> entry : sortedExamples.entrySet()) {
                LOGGER.info(entry.getKey() + ". " + entry.getValue().getName());
            }

            LOGGER.info("\nType 'exit' to quit.");
            LOGGER.info("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(choice)) {
                break;
            }

            ExampleConfig selectedExample = examples.get(choice);

            if (selectedExample != null) {
                runExample(selectedExample);
                LOGGER.info("\nPress Enter to return to the main menu...");
                scanner.nextLine();
            } else {
                LOGGER.warning("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void runExample(ExampleConfig selectedExample) {
        LOGGER.info("\n--- Running " + selectedExample.getName() + " ---\n");

        try {
            Class<?> clazz = Class.forName(selectedExample.getType());
            Method runMethod = clazz.getMethod("run");
            runMethod.invoke(null);
            
        } catch (ClassNotFoundException e) {
            LOGGER.severe("[ERROR] Could not find class: " + selectedExample.getType());
        } catch (NoSuchMethodException e) {
            LOGGER.severe("[ERROR] Could not find a public static run() method on " + selectedExample.getType());
        } catch (Exception e) {
            LOGGER.severe("[ERROR] An error occurred while running the example: " + e.getMessage());
        }
    }
}

class ExampleConfig {
    private String name;
    private String type;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}