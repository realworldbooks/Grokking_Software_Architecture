package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements a Weighted Decision Model to choose the best option from a set of choices.
 * This model provides a quantitative and data-driven way to make architectural decisions.
 * * ARCHITECTURAL NOTE: Encapsulating the Algorithm
 * The logic for calculating the winner is isolated here. If we decide to change 
 * our mathematical model later (e.g., using a logarithmic scale instead of linear), 
 * we only have to update this one class.
 */
public class DecisionMaker {

    /**
     * Picks the best option based on a set of weighted criteria.
     * * @param options A list of options to evaluate. Each option has scores for various criteria.
     * @param weights A map where the key is the criterion name and the value is its importance (weight).
     * @return A DecisionResult containing the name of the best option and a string explaining the rationale.
     */
    public DecisionResult pickOption(List<Option> options, Map<String, Double> weights) {
        Option bestOption = null;
        double highestScore = Double.NEGATIVE_INFINITY;
        List<String> details = new ArrayList<>();

        for (Option opt : options) {
            // THE CORE LOGIC: Calculate the weighted score for this option.
            // For each criterion (e.g., "performance", "cost"), we multiply the option's
            // score for that criterion (e.g., 4/5) by the weight we've assigned to that
            // criterion (e.g., 60% importance). We sum these products to get the final score.
            // Formula: FinalScore = (Score_A * Weight_A) + (Score_B * Weight_B) + ...
            double score = 0.0;
            for (Map.Entry<String, Double> weightEntry : weights.entrySet()) {
                String criteria = weightEntry.getKey();
                Double weight = weightEntry.getValue();
                
                int optionScore = opt.getScores().getOrDefault(criteria, 0);
                score += optionScore * weight;
            }

            details.add(String.format("%s: %.2f", opt.getName(), score));

            if (score > highestScore) {
                highestScore = score;
                bestOption = opt;
            }
        }

        // The rationale provides a transparent explanation for the decision,
        // which is crucial for communicating architectural choices to a team.
        String weightsString = "{" + weights.entrySet().stream()
                .map(kv -> "'" + kv.getKey() + "': " + kv.getValue())
                .collect(Collectors.joining(", ")) + "}";
                
        String bestOptionName = bestOption != null ? bestOption.getName() : "None";
        String rationale = String.format("Scores: %s\n -> Based on weights %s, we pick **%s**.",
                String.join(" | ", details), weightsString, bestOptionName);
        
        return new DecisionResult(bestOptionName, rationale);
    }
}