package com.grokkingsoftwarearchitecture.chapter02.weighteddecisionmodel;

import java.util.Map;

public class Option {
    public final String name;
    public final Map<String, Integer> scores;

    public Option(String name, Map<String, Integer> scores) {
        this.name = name;
        this.scores = scores;
    }
}