// infrastructure/VectorDbSimulator.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VectorDbSimulator {
    public record VectorRecord(String id, double[] vector, String name) {}
    private final List<VectorRecord> vectors = new ArrayList<>();

    public void upsert(String id, double[] vector, String name) {
        vectors.add(new VectorRecord(id, vector, name));
    }

    public List<String> query(double[] queryVector, int topK) {
        return vectors.stream()
                .sorted(Comparator.comparingDouble(v -> getDistance(v.vector(), queryVector)))
                .limit(topK)
                .map(VectorRecord::name)
                .collect(Collectors.toList());
    }

    private double getDistance(double[] vec1, double[] vec2) {
        double sum = 0;
        for (int i = 0; i < vec1.length; i++) {
            sum += Math.pow(vec1[i] - vec2[i], 2);
        }
        return Math.sqrt(sum);
    }
}