package com.architecturebook.chapter2.weighteddecisionmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecisionMaker {
    public DecisionResult pickOption(List<Option> options, Map<String, Double> weights) {
        Option bestOption = null;
        double highestScore = Double.NEGATIVE_INFINITY;
        List<String> details = new ArrayList<>();

        for (Option opt : options) {
            double score = 0.0;
            // Calculate the weighted score for this option
            for (Map.Entry<String, Double> weightEntry : weights.entrySet()) {
                score += opt.scores.getOrDefault(weightEntry.getKey(), 0) * weightEntry.getValue();
            }
            details.add(String.format("%s: %.2f", opt.name, score));

            if (score > highestScore) {
                highestScore = score;
                bestOption = opt;
            }
        }

        String rationale = String.format("Scores: %s\n -> Based on weights %s, we pick **%s**.",
                String.join(" | ", details), 
                weights.toString().replace("=", ": "), // Formatting to look slightly more like JSON/Python dicts
                bestOption != null ? bestOption.name : "None");

        return new DecisionResult(bestOption != null ? bestOption.name : "None", rationale);
    }
}