// infrastructure/NoSqlSimulator.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoSqlSimulator {
    public record Document(String name, List<String> tags) {}
    private final List<Document> collection = new ArrayList<>();

    public void insertOne(Document document) {
        collection.add(document);
    }

    public List<String> findByTag(String tag) {
        return collection.stream()
                .filter(doc -> doc.tags().contains(tag))
                .map(Document::name)
                .collect(Collectors.toList());
    }
}