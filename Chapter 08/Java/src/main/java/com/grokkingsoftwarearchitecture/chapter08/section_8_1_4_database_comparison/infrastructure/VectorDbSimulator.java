package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * THE VECTOR WAY (INFRASTRUCTURE LAYER): Math, not Magic.
 * Calculates intent using high-dimensional distance rather than exact spelling.
 */
public class VectorDbSimulator {
    private final List<VectorRecord> vectors = new ArrayList<>();

    public void upsert(String id, double[] vector, String name) {
        VectorRecord record = new VectorRecord();
        record.id = id;
        record.vector = vector;
        record.name = name;
        vectors.add(record);
    }

    public List<String> query(double[] queryVector, int topK) {
        // Sort the database by the shortest mathematical distance to the user's query
        return vectors.stream()
                .sorted(Comparator.comparingDouble(v -> getDistance(v.vector, queryVector)))
                .limit(topK)
                .map(v -> v.name)
                .collect(Collectors.toList());
    }

    private double getDistance(double[] vec1, double[] vec2) {
        // Standard Euclidean Distance: Calculates how far apart the two meanings are
        double sum = 0;
        for (int i = 0; i < vec1.length; i++) {
            sum += Math.pow(vec1[i] - vec2[i], 2);
        }
        return Math.sqrt(sum);
    }
}