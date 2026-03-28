package com.grokkingsoftwarearchitecture.chapter02.section_2_7_1_weighted_decision_model;

import java.util.Map;

public class Option {
    public final String name;
    public final Map<String, Integer> scores;

    public Option(String name, Map<String, Integer> scores) {
        this.name = name;
        this.scores = scores;
    }
}