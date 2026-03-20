package com.grokkingsoftwarearchitecture.chapter02.constraintsinaction;

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