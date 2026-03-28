package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

import java.util.Map;

/**
 * Represents a single architectural choice to be evaluated.
 * This is a simple data-holding class (POCO).
 * * ARCHITECTURAL NOTE: Data Structures
 * By keeping this class purely for data, we can easily serialize it, 
 * deserialize it from a database, or pass it around without dragging 
 * any heavy calculation logic along with it.
 */
public class Option {
    /**
     * The name of the architectural option (e.g., "Redis", "In-Memory Cache").
     */
    private String name;

    /**
     * A dictionary holding the scores for this option against various criteria.
     * The key is the criterion name (e.g., "performance", "cost") and the value
     * is the score, typically on a scale (e.g., 1 to 5).
     */
    private Map<String, Integer> scores;

    public Option(String name, Map<String, Integer> scores) {
        this.name = name;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }
}