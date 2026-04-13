// services/MockOpenAiService.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.services;

public class MockOpenAiService {
    public static double[] createEmbedding(String text) {
        if ("Lasagna".equals(text)) return new double[]{0.9, 0.9, 0.1};
        if ("Comfort Food".equals(text)) return new double[]{0.8, 0.9, 0.2};
        if ("Healthy Salad".equals(text)) return new double[]{0.1, 0.1, 0.9};
        return new double[]{0.0, 0.0, 0.0};
    }
}