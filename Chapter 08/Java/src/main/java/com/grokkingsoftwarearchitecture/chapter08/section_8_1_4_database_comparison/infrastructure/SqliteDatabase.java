// infrastructure/SqliteDatabase.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ARCHITECTURAL NOTE: THE QUARANTINE ZONE
 * This class belongs in the 'infrastructure' package because it represents 
 * the "Dirty" Outside World. 
 * * This is a raw ENGINE. It understands technical implementation details 
 * like SQL syntax and memory allocation. Crucially, it knows absolutely 
 * NOTHING about our business rules. 
 * * THE DATABASE (INFRASTRUCTURE LAYER): Strict, organized. Like a filing cabinet.
 */
public class SqliteDatabase implements AutoCloseable {
    private Connection connection;

    public SqliteDatabase() {
        try {
            // We use an in-memory SQLite database so it runs instantly without file setup
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE Recipes (id INTEGER, name TEXT, type TEXT)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void insert(int id, String name, String type) {
        String sql = "INSERT INTO Recipes (id, name, type) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed", e);
        }
    }

    // The naive literal search
    public List<String> queryByName(String name) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT name FROM Recipes WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query failed", e);
        }
        return results;
    }

    public List<String> queryByType(String type) {
        // Exact keyword match required. If you search for "Italian", you find NOTHING.
        List<String> results = new ArrayList<>();
        String sql = "SELECT name FROM Recipes WHERE type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query failed", e);
        }
        return results;
    }

    public void executeRaw(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database", e);
        }
    }
}