// infrastructure/NoSqlSimulator.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * THE DOCUMENT WAY (INFRASTRUCTURE LAYER): Fast, loose. Like a messy desk.
 * Simulates MongoDB's document storage using standard Java lists and objects.
 */
public class NoSqlSimulator {
    public final List<NoSqlDocument> collection = new ArrayList<>();

    public void insertOne(NoSqlDocument document) {
        collection.add(document);
    }

    // The naive literal search
    public List<String> findByName(String name) {
        return collection.stream()
                .filter(doc -> doc.name.equals(name))
                .map(doc -> doc.name)
                .collect(Collectors.toList());
    }

    public List<String> findByTag(String tag) {
        // Contains Match: Better, but still relies on exact spelling of the tag.
        return collection.stream()
                .filter(doc -> doc.tags.contains(tag))
                .map(doc -> doc.name)
                .collect(Collectors.toList());
    }
}