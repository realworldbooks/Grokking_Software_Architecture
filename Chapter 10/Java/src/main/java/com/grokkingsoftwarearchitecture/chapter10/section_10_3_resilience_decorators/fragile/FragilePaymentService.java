package com.grokking.chapter10.resilience.infrastructure;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * THE FRAGILE IMPLEMENTATION:
 * * DESIGN NOTE:
 * This class represents the "Naive" way of handling external dependencies in Java. 
 * It treats a remote network call as if it were a local, reliable method call.
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. TEMPORAL COUPLING: This method is "Locked" to the network. Without a timeout 
 * or retry policy, the calling thread is held hostage by Zebra's server. If the
 * network hangs, this thread is essentially dead until the OS kills it.
 * * 2. ABSTRACTION LEAK: There is no Interface (Port). The business logic is forced 
 * to depend directly on this concrete implementation and the 'java.net.http' 
 * library, violating the Downward Dependency Rule (Chapter 4).
 * * 3. THE HAPPY PATH FALLACY: The code assumes that if it sends a request, it 
 * receives a success. It provides no mechanism to survive a 503 (Service 
 * Unavailable) or a temporary network blip. Failure here is absolute and total.
 */
public class FragilePaymentService {

    // A raw, unprotected HTTP client.
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .build();

    /**
     * A raw function sitting right in the middle of your business logic.
     * It assumes the outside world is stable and friendly.
     */
    public String chargeCreditCard(double amount) throws Exception {
        System.out.println("      [Payment Service] Attempting to charge $" + amount + "...");

        // Constructing a hardcoded JSON payload
        String jsonBody = "{\"amount\": " + amount + ", \"order_id\": \"12345\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.zebra.com/charge"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // If Zebra is down or the internet blips, this throws an IOException 
        // and crashes the application flow instantly.
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // A naive check: if it's not 200, we just throw an exception.
        // No retries, no backoff, no mercy.
        if (response.statusCode() >= 400) {
            throw new RuntimeException("API Error: Status Code " + response.statusCode());
        }

        return response.body();
    }
}