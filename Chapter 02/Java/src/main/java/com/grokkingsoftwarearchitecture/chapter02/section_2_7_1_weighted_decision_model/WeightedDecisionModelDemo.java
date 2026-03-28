package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates how to configure and execute the Weighted Decision Model.
 */
public class WeightedDecisionModelDemo {
    public static void run() {
        System.out.println("--- Weighted Decision Model Example ---");

        // STEP 1: Define the architectural options and score them.
        // As a team, you would evaluate each option against a set of criteria that
        // are important to your project. Here, we're deciding on a caching strategy.
        // We score each option on a scale of 1 (bad) to 5 (good) for each criterion.
        List<Option> options = Arrays.asList(
            new Option("InMemory", Map.of("availability", 1, "performance", 5, "simplicity", 5)),
            new Option("Redis",    Map.of("availability", 5, "performance", 4, "simplicity", 3)),
            new Option("Database", Map.of("availability", 4, "performance", 2, "simplicity", 4))
        );

        DecisionMaker decisionMaker = new DecisionMaker();

        // ---
        // SCENARIO 1: The project's highest priority is high availability.
        // ---
        System.out.println("\n[SCENARIO 1: Prioritizing Availability]");
        
        // STEP 2: Define the weights based on current priorities.
        // The weights represent the relative importance of each criterion. They should sum to 1.0.
        // Here, "availability" is paramount, so it gets a high weight of 0.6 (or 60%).
        Map<String, Double> availabilityFocusedWeights = Map.of(
            "availability", 0.6, 
            "performance", 0.3, 
            "simplicity", 0.1
        );
        
        // STEP 3: Run the model and get the decision.
        DecisionResult result1 = decisionMaker.pickOption(options, availabilityFocusedWeights);
        System.out.println(result1.getRationale());
        // With these weights, Redis is the clear winner because of its high availability score.

        // ---
        // SCENARIO 2: Project priorities change. Now, raw performance and simplicity are key.
        // ---
        System.out.println("\n[SCENARIO 2: Prioritizing Performance & Simplicity]");
        
        // STEP 2 (Re-run): Define a new set of weights reflecting the new priorities.
        Map<String, Double> performanceFocusedWeights = Map.of(
            "availability", 0.1, 
            "performance", 0.5, 
            "simplicity", 0.4
        );
        
        // STEP 3 (Re-run): Get the new decision.
        DecisionResult result2 = decisionMaker.pickOption(options, performanceFocusedWeights);
        System.out.println(result2.getRationale());
        // By simply changing the weights, the model now recommends the InMemory option,
        // demonstrating how this tool can adapt to different project needs.

        System.out.println("---------------------------------------\n");
    }
}