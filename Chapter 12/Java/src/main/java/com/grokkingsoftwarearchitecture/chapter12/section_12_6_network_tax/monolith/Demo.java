package com.grokkingsoftwarearchitecture.chapter12.section_12_6_network_tax.monolith;

import java.util.Map;

public class Demo {

    private static final Map<Integer, String> AUTHORS = Map.of(
        1, "Ada Lovelace",
        2, "Alan Turing"
    );

    private static final Map<Integer, String> POSTS = Map.of(
        1, "The Analytical Engine: A Deep Dive",
        2, "Can Machines Think?"
    );

    static class DatabaseException extends RuntimeException {
        DatabaseException(String msg) { super(msg); }
    }

    static class NotFoundException extends RuntimeException {
        NotFoundException(int blogId) { super("Blog post with id " + blogId + " was not found."); }
    }

    static class BlogDetailsViewModel {
        final int id;
        final String title;
        final String authorName;
        final int commentCount;

        BlogDetailsViewModel(int id, String title, String authorName, int commentCount) {
            this.id = id;
            this.title = title;
            this.authorName = authorName;
            this.commentCount = commentCount;
        }
    }

    static class MockBlogDatabase {
        boolean isAvailable = true;

        BlogDetailsViewModel getBlogDetails(int blogId) {
            if (!isAvailable) throw new DatabaseException("Database unavailable.");
            try { Thread.sleep(5); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            if (!POSTS.containsKey(blogId)) throw new NotFoundException(blogId);
            return new BlogDetailsViewModel(blogId, POSTS.get(blogId), AUTHORS.get(blogId), 2);
        }
    }

    public static void run() {
        System.out.println("\n=== Section 12.6.1: The Monolith Approach (Java) ===");
        System.out.println("THE SETUP: A Blog webpage needs the Post, the Author, and the Comments.");
        System.out.println("THE MONOLITH: All data lives in ONE database, all code lives in ONE process.\n");

        MockBlogDatabase db = new MockBlogDatabase();

        System.out.println("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
        System.out.println("  [ORM] Posts.Include(Author).Include(Comments).FirstOrDefault(id=1)");
        System.out.println("  [SQL] ONE query. ONE round trip. ZERO network latency\n");

        long start = System.nanoTime();
        try {
            BlogDetailsViewModel vm = db.getBlogDetails(1);
            long elapsed = (System.nanoTime() - start) / 1_000_000;
            System.out.println("  [Result] Blog Post     : #" + vm.id + " " + vm.title);
            System.out.println("  [Result] Author        : " + vm.authorName);
            System.out.println("  [Result] Comments      : " + vm.commentCount);
            System.out.println("  [Latency] " + elapsed + " ms (5 ms single query)\n");
        } catch (DatabaseException e) {
            System.out.println("  [Result] FAILED: " + e.getMessage() + "\n");
        }

        System.out.println("--- SCENARIO 2: The Database is DOWN ---");
        System.out.println("  [Database] Simulating outage...");
        db.isAvailable = false;
        try {
            db.getBlogDetails(1);
        } catch (DatabaseException e) {
            System.out.println("  [Result] The whole query fails together.");
            System.out.println("  [Result] No partial state. No half-rendered page. Predictable failure.\n");
        } finally {
            db.isAvailable = true;
        }

        System.out.println("--- SCENARIO 3: Blog Post NOT Found ---");
        try {
            db.getBlogDetails(999);
        } catch (NotFoundException e) {
            System.out.println("  [Result] NotFoundException: " + e.getMessage() + "\n");
        }

        System.out.println("ARCHITECTURAL VERDICT: THE MONOLITH WINS THE SIMPLICITY CONTEST");
        System.out.println("  - A single SQL JOIN returns everything. Zero network calls.");
        System.out.println("  - If the database is down, the whole query fails together.");
        System.out.println("  - No async/await, no HTTP status handling, no fallback strategies.\n");
    }
}
