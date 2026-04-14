// services/MockOpenAiService.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.services;

/**
 * EXTERNAL API SIMULATOR: 
 * In a real application, you don't manually calculate vectors. You call an 
 * AI service (OpenAI, Cohere, etc.) that returns the vector array for you.
 * * This simulates converting concepts into a 3-dimensional array: 
 * [Food_Score, Comfort_Score, Health_Score]
 */
public class MockOpenAiService {
    
    public static double[] createEmbedding(String text) {
        // Perfect spelling baseline
        if (text.equals("Lasagna")) return new double[] { 0.9, 0.9, 0.1 };
        
        // Categorical relationship: Pasta is mathematically very close to Lasagna
        if (text.equals("Pasta")) return new double[] { 0.85, 0.85, 0.15 };
        
        // Typo tolerance
        if (text.equals("Lasnga")) return new double[] { 0.88, 0.85, 0.12 };
        
        // Other distinct concepts
        if (text.equals("Healthy Salad")) return new double[] { 0.1, 0.1, 0.9 };
        if (text.equals("Comfort Food")) return new double[] { 0.8, 0.9, 0.2 };
        if (text.equals("Mac & Cheese")) return new double[] { 0.85, 0.95, 0.05 };
        if (text.equals("Beef Stew")) return new double[] { 0.75, 0.85, 0.3 };
        
        return new double[] { 0.0, 0.0, 0.0 };
    }
}