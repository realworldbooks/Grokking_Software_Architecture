package com.grokkingsoftwarearchitecture.chapter02.section_2_4_1_constraints_in_action;

import java.util.concurrent.CompletableFuture;

public class Database {
    public CompletableFuture<User> fetchUserData(String userId) {
        if ("User123".equals(userId)) {
            return CompletableFuture.completedFuture(
                new User("User123", "Alice", "alice@example.com")
            );
        }
        return CompletableFuture.completedFuture(null);
    }
}