package com.grokkingsoftwarearchitecture.chapter12.section_12_6_network_tax.microservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Demo {

    // ------------------------------------------------------------------
    // Mock HTTP response + service clients (simulates the network)
    // ------------------------------------------------------------------

    static class MockHttpResponse {
        final int statusCode;
        final Map<String, Object> payload;
        final boolean isSuccess;

        MockHttpResponse(int statusCode, Map<String, Object> payload) {
            this.statusCode = statusCode;
            this.payload = payload;
            this.isSuccess = statusCode >= 200 && statusCode < 300;
        }
    }

    static class MockPostServiceClient {
        boolean isAvailable = true;

        CompletableFuture<MockHttpResponse> getPost(int id) {
            return CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(30); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (!isAvailable) return new MockHttpResponse(503, Map.of("error", "Service Unavailable"));

                Map<Integer, Map<String, Object>> posts = new HashMap<>();
                posts.put(1, Map.of("id", 1, "title", "The Analytical Engine: A Deep Dive", "body", "Charles Babbage's vision was centuries ahead..."));
                posts.put(2, Map.of("id", 2, "title", "Can Machines Think?", "body", "The question that launched a thousand papers..."));

                return posts.containsKey(id)
                    ? new MockHttpResponse(200, posts.get(id))
                    : new MockHttpResponse(404, Map.of("error", "Not Found"));
            });
        }
    }

    static class MockAuthorServiceClient {
        boolean isAvailable = true;

        CompletableFuture<MockHttpResponse> getAuthor(int blogId) {
            return CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(30); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (!isAvailable) return new MockHttpResponse(503, Map.of("error", "Service Unavailable"));

                Map<Integer, Map<String, Object>> authors = new HashMap<>();
                authors.put(1, Map.of("id", 1, "name", "Ada Lovelace", "email", "ada@byte-blog.dev"));
                authors.put(2, Map.of("id", 2, "name", "Alan Turing", "email", "alan@byte-blog.dev"));

                return authors.containsKey(blogId)
                    ? new MockHttpResponse(200, authors.get(blogId))
                    : new MockHttpResponse(404, Map.of("error", "Not Found"));
            });
        }
    }

    static class MockCommentServiceClient {
        boolean isAvailable = true;

        CompletableFuture<MockHttpResponse> getComments(int blogId) {
            return CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(30); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (!isAvailable) return new MockHttpResponse(503, Map.of("error", "Service Unavailable"));

                Map<Integer, List<Map<String, Object>>> comments = new HashMap<>();
                comments.put(1, List.of(
                    Map.of("id", 1, "authorName", "Grace Hopper", "text", "Lovely write-up on analytical engines!"),
                    Map.of("id", 2, "authorName", "Linus Torvalds", "text", "Now do the same for kernel scheduling.")
                ));
                comments.put(2, List.of(
                    Map.of("id", 3, "authorName", "Ada Lovelace", "text", "The Turing Test section is a classic.")
                ));

                return new MockHttpResponse(200, Map.of("comments", comments.getOrDefault(blogId, List.of())));
            });
        }
    }

    // ------------------------------------------------------------------
    // Aggregator Gateway — Listing 12.2
    // ------------------------------------------------------------------

    static class PostServiceUnavailableException extends RuntimeException {
        PostServiceUnavailableException(String msg) { super(msg); }
    }

    static class BlogDetailsResult {
        Map<String, Object> post;
        Map<String, Object> author;
        List<Map<String, Object>> comments;

        BlogDetailsResult(Map<String, Object> post, Map<String, Object> author, List<Map<String, Object>> comments) {
            this.post = post;
            this.author = author;
            this.comments = comments;
        }
    }

    static class AggregatorGateway {
        private final MockPostServiceClient postClient;
        private final MockAuthorServiceClient authorClient;
        private final MockCommentServiceClient commentClient;

        AggregatorGateway(MockPostServiceClient p, MockAuthorServiceClient a, MockCommentServiceClient c) {
            this.postClient = p;
            this.authorClient = a;
            this.commentClient = c;
        }

        BlogDetailsResult getBlogDetails(int blogId) throws Exception {
            // TAX 1: The Latency Tax — fire 3 HTTP calls across the wire.
            CompletableFuture<MockHttpResponse> postTask = postClient.getPost(blogId);
            CompletableFuture<MockHttpResponse> authorTask = authorClient.getAuthor(blogId);
            CompletableFuture<MockHttpResponse> commentsTask = commentClient.getComments(blogId);

            // TAX 2: The Reliability Tax — wait for the network.
            CompletableFuture.allOf(postTask, authorTask, commentsTask).join();

            MockHttpResponse postResponse = postTask.get();
            if (!postResponse.isSuccess)
                throw new PostServiceUnavailableException("Core post data unavailable.");

            MockHttpResponse authorResponse = authorTask.get();
            MockHttpResponse commentsResponse = commentsTask.get();

            Map<String, Object> post = postResponse.payload;
            Map<String, Object> author = authorResponse.isSuccess ? authorResponse.payload : Map.of();

            // TAX 3: Partial Failures — serve page without comments.
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> comments = commentsResponse.isSuccess
                ? (List<Map<String, Object>>) commentsResponse.payload.get("comments")
                : List.of();

            return new BlogDetailsResult(post, author, comments);
        }
    }

    // ------------------------------------------------------------------
    // Demo
    // ------------------------------------------------------------------

    public static void run() throws Exception {
        System.out.println("\n=== Section 12.6.2: The Microservice Aggregator (Java) ===");
        System.out.println("THE SETUP: The SAME Blog webpage, but the team used the Strangler Fig.");
        System.out.println("THE MICROSERVICES: PostService, AuthorService, CommentService -");
        System.out.println("each with its OWN database, spread across the network.\n");

        MockPostServiceClient postClient = new MockPostServiceClient();
        MockAuthorServiceClient authorClient = new MockAuthorServiceClient();
        MockCommentServiceClient commentClient = new MockCommentServiceClient();
        AggregatorGateway gateway = new AggregatorGateway(postClient, authorClient, commentClient);

        System.out.println("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
        System.out.println("  [Gateway] Firing 3 HTTP calls CONCURRENTLY across the wire:");
        System.out.println("    GET http://post-service/api/posts/1");
        System.out.println("    GET http://author-service/api/authors/1");
        System.out.println("    GET http://comment-service/api/comments/1\n");

        long start = System.nanoTime();
        try {
            BlogDetailsResult result = gateway.getBlogDetails(1);
            long elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            System.out.println("  [Result] Blog Post     : #" + result.post.get("id") + " " + result.post.get("title"));
            System.out.println("  [Result] Author        : " + result.author.get("name"));
            System.out.println("  [Result] Comments      : " + result.comments.size());
            System.out.println("  [Latency] " + elapsed + " ms (3 x 30ms network calls)\n");
        } catch (Exception e) {
            System.out.println("  [Result] FAILED: " + e.getMessage() + "\n");
        }

        System.out.println("--- SCENARIO 2: The Comment Service is DOWN (Partial Failure) ---");
        System.out.println("  [CommentService] Simulating outage...");
        commentClient.isAvailable = false;
        try {
            BlogDetailsResult result = gateway.getBlogDetails(1);
            System.out.println("  [Result] Blog Post     : #" + result.post.get("id") + " " + result.post.get("title"));
            System.out.println("  [Result] Author        : " + result.author.get("name"));
            System.out.println("  [Result] Comments      : " + result.comments.size() + " (FALLBACK: served without comments)\n");
        } catch (Exception e) {
            System.out.println("  [Result] FAILED: " + e.getMessage() + "\n");
        } finally {
            commentClient.isAvailable = true;
        }

        System.out.println("--- SCENARIO 3: The Post Service is DOWN (Core Failure) ---");
        System.out.println("  [PostService] Simulating outage...");
        postClient.isAvailable = false;
        try {
            gateway.getBlogDetails(1);
        } catch (PostServiceUnavailableException e) {
            System.out.println("  [Result] FAILED: " + e.getMessage());
            System.out.println("  [Result] Core post data unavailable - we MUST abort the whole page.\n");
        } finally {
            postClient.isAvailable = true;
        }

        System.out.println("ARCHITECTURAL VERDICT: THE MICROSERVICE PAYS THE NETWORK TAX");
        System.out.println("  - 3 separate HTTP calls across the wire (even when concurrent).");
        System.out.println("  - We must manually write fallback logic for partial failures.");
        System.out.println("  - The engineering complexity skyrocketed.\n");
    }
}
