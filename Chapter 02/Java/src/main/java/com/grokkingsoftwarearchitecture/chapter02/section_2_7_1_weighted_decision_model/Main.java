package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Running Weighted Decision Model Example ---");

        // 1. Define our options and score them from 1 (worst) to 5 (best)
        List<Option> options = Arrays.asList(
            new Option("InMemory", Map.of("availability", 1, "performance", 5, "simplicity", 5)),
            new Option("Redis",    Map.of("availability", 5, "performance", 4, "simplicity", 3)),
            new Option("Database", Map.of("availability", 4, "performance", 2, "simplicity", 4))
        );

        DecisionMaker decisionMaker = new DecisionMaker();

        // 2. Define our priorities: Availability is most important (60%).
        System.out.println("\nScenario 1: Prioritizing Availability");
        Map<String, Double> ourPriorities = Map.of("availability", 0.6, "performance", 0.3, "simplicity", 0.1);
        
        // 3. Get the decision!
        DecisionResult result1 = decisionMaker.pickOption(options, ourPriorities);
        System.out.println(result1.rationale);

        // 4. Define new priorities: Performance and Simplicity are most important.
        System.out.println("\nScenario 2: Prioritizing Performance & Simplicity");
        Map<String, Double> newPriorities = Map.of("availability", 0.1, "performance", 0.5, "simplicity", 0.4);
        
        DecisionResult result2 = decisionMaker.pickOption(options, newPriorities);
        System.out.println(result2.rationale);

        System.out.println("-----------------------------------------------");
    }
}