package com.grokkingsoftwarearchitecture.chapter06;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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

            // Flat schema: the whole file is a map of "id" -> ExampleConfig
            Map<String, ExampleConfig> examples = mapper.readValue(
                    configFile, new TypeReference<Map<String, ExampleConfig>>() {});

            // Sort examples numerically
            TreeMap<String, ExampleConfig> sortedExamples = new TreeMap<>(
                    (k1, k2) -> Integer.compare(Integer.parseInt(k1), Integer.parseInt(k2))
            );
            sortedExamples.putAll(examples);

            while (true) {
                System.out.println("\n=== Grokking Software Architecture Chapter 06: Java Examples ===\n");

                for (Map.Entry<String, ExampleConfig> entry : sortedExamples.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }

                System.out.print("\nType 'exit' to quit or enter your choice: ");
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("exit")) break;

                ExampleConfig selected = examples.get(choice);
                if (selected != null) {
                    runExample(selected, args);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("[CRITICAL ERROR] " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void runExample(ExampleConfig config, String[] args) {
        try {
            Class<?> clazz = Class.forName(config.getType());
            boolean isSpringBoot = config.isSpringBoot();

            if (isSpringBoot) {
                // Spring Boot handles its own logging/startup
                SpringApplication.run(clazz, args);
            } else {
                // Standard Java execution for REST/GraphQL demos
                Method runMethod = clazz.getMethod("run");
                runMethod.invoke(null);

                System.out.println("\nPress ENTER to return to the main menu...");
                new Scanner(System.in).nextLine();
            }
        } catch (Exception e) {
            System.err.println("[LAUNCH ERROR] Could not run " + config.getType() + ": " + e.getMessage());
        }
    }
}

/**
 * Maps to each entry in the flat Examples.json schema.
 */
class ExampleConfig {
    private String name;
    private String type;
    @JsonProperty("isSpringBoot")
    private boolean isSpringBoot;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isSpringBoot() { return isSpringBoot; }
    public void setSpringBoot(boolean springBoot) { isSpringBoot = springBoot; }
}
