package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

// A simple container since Java doesn't have built-in Tuples
public class DecisionResult {
    public final String bestOption;
    public final String rationale;

    public DecisionResult(String bestOption, String rationale) {
        this.bestOption = bestOption;
        this.rationale = rationale;
    }
}