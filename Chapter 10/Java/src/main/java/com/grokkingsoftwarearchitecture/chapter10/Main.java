package com.grokkingsoftwarearchitecture.chapter10;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File("Examples.json");

        if (!configFile.exists()) {
            System.out.println("[ERROR] Examples.json not found!");
            return;
        }

        try {
            
            ChapterConfig config = mapper.readValue(configFile, ChapterConfig.class);
            
            // Sort examples numerically
            TreeMap<String, ExampleConfig> sortedExamples = new TreeMap<>(
                    (k1, k2) -> Integer.compare(Integer.parseInt(k1), Integer.parseInt(k2))
            );
            sortedExamples.putAll(config.getExamples());

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n\n=== " + config.getTitle() + " ===\n");

                for (Map.Entry<String, ExampleConfig> entry : sortedExamples.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue().getName());
                }

                System.out.print("\nEnter your choice (or 'exit'): ");
                String choice = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(choice)) break;

                ExampleConfig selected = config.getExamples().get(choice);
                if (selected != null) {
                    runExample(selected);
                    System.out.println("\nPress Enter to return to menu...");
                    scanner.nextLine();
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load lab configuration.");
            e.printStackTrace();
        }
    }

    private static void runExample(ExampleConfig config) {
        try {
            Class<?> clazz = Class.forName(config.getType());
            Method runMethod;
            try {
                runMethod = clazz.getMethod("runAsync");
            } catch (NoSuchMethodException e) {
                runMethod = clazz.getMethod("run");
            }
            runMethod.invoke(null);
        } catch (Exception e) {
            System.out.println("[ERROR] Execution failed: " + e.getMessage());
        }
    }
}

/**
 * THE ROOT CONFIGURATION:
 * Matches the top-level structure of Examples.json.
 */
class ChapterConfig {
    private String chapter;
    private String title;
    private Map<String, ExampleConfig> examples;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Map<String, ExampleConfig> getExamples() { return examples; }
    public void setExamples(Map<String, ExampleConfig> examples) { this.examples = examples; }
    public String getChapter() { return chapter; }
    public void setChapter(String chapter) { this.chapter = chapter; }
}

class ExampleConfig {
    private String name;
    private String type;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}