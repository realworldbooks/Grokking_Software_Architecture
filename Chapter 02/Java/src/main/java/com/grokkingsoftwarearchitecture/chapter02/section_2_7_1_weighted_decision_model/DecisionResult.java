package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;
/**
 * Represents the outcome of a decision-making process.
 * * ARCHITECTURAL NOTE: strongly-typed return objects.
 * Instead of using an inner class or a generic Map to return multiple values, 
 * extracting this into its own file provides a clean, strictly defined contract 
 * for what the DecisionMaker will return.
 */
public class DecisionResult {
    private final String bestOption;
    private final String rationale;

    public DecisionResult(String bestOption, String rationale) {
        this.bestOption = bestOption;
        this.rationale = rationale;
    }

    public String getBestOption() {
        return bestOption;
    }

    public String getRationale() {
        return rationale;
    }
}