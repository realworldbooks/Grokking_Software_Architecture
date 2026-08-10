package com.grokkingsoftwarearchitecture.chapter14;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Method;

public class Main {

    public static class ExampleConfig {
        public String name;
        public String type;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        File configFile = new File("Examples.json");
        if (!configFile.exists()) configFile = new File(System.getProperty("user.dir"), "Examples.json");
        if (!configFile.exists()) {
            System.err.println("[ERROR] Examples.json not found!");
            return;
        }

        String json = Files.readString(configFile.toPath());
        Map<String, ExampleConfig> examples = new Gson().fromJson(json,
            new TypeToken<Map<String, ExampleConfig>>() {}.getType());
        Map<String, ExampleConfig> sorted = new TreeMap<>(examples);

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println("=== Grokking Software Architecture Chapter 14: Java Examples ===\n");
            sorted.forEach((k, v) -> System.out.println(k + ". " + v.name));
            System.out.println("\nType 'exit' to quit.");
            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("exit")) break;

            if (examples.containsKey(choice)) {
                ExampleConfig selected = examples.get(choice);
                System.out.print("\033[H\033[2J");
                System.out.println("--- Running " + selected.name + " ---\n");
                try {
                    Class<?> cls = Class.forName(selected.type);
                    Method m = cls.getMethod("run");
                    m.invoke(null);
                } catch (Exception e) {
                    System.err.println("[ERROR] Execution failed: " + (e.getCause() != null ? e.getCause().getMessage() : e.getMessage()));
                }
                System.out.println("\nPress Enter to return to the main menu...");
                scanner.nextLine();
            } else {
                System.out.println("\nInvalid choice. Press Enter to try again...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}