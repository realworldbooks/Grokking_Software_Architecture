package com.grokkingsoftwarearchitecture.chapter02.section_2_4_1_constraints_in_action;

import java.util.concurrent.CompletableFuture;

/**
 * Simulates a Data Access Layer (DAL) or "Service" class.
 * Its single responsibility is to handle all interactions with the database.
 * This separation of concerns means that if we were to change our database technology
 * (e.g., from SQL Server to MongoDB), this is the only class we would need to modify.
 * The `ExportController` would remain unchanged.
 */
public class Database {
    /**
     * Fetches a user's data from the database.
     * * @param userId The ID of the user to fetch.
     * @return A CompletableFuture containing a `User` object if the user is found; otherwise, `null`.
     * * ARCHITECTURAL NOTE: The Nullable Constraint
     * Returning a potentially null `User` is an explicit design choice. It forces the
     * calling code (the controller) to acknowledge and handle the possibility
     * that the user may not exist, which is a crucial business constraint.
     */
    public CompletableFuture<User> fetchUserDataAsync(String userId) {
        // Simulating an asynchronous database call.
        if ("User123".equals(userId)) {
            User mockUser = new User("User123", "Alice", "alice@example.com");
            return CompletableFuture.completedFuture(mockUser);
        }
        // If the user is not found, we return null to signal this to the caller.
        return CompletableFuture.completedFuture(null);
    }
}