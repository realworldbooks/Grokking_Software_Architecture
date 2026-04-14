package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockSqlDatabase {
    // Simulating rigid columns
    record RecipeRow(int id, String name, String type) {}
    private final List<RecipeRow> table = new ArrayList<>();

    public void insert(int id, String name, String type) {
        table.add(new RecipeRow(id, name, type));
    }

    public List<String> queryByType(String type) {
        return table.stream()
                .filter(row -> row.type().equals(type))
                .map(RecipeRow::name)
                .collect(Collectors.toList());
    }
}