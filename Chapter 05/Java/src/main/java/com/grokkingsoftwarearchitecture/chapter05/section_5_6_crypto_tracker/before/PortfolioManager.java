package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.before;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * THE CORE (Tightly Coupled).
 * WARNING: This class is a "Liability" because it violates the 
 * Golden Rule of Separation of Concerns. [cite: 1]
 */
public class PortfolioManager {

    /**
     * Checks the server temperature and sends an alert if it's too high.
     * In a real system, this is the "Chaotic Outside World"[cite: 1].
     */
    public double calculateTotalValue(double btcAmount) {
        
        // FIX 1: Try-with-resources handles the "Close this HttpClient" warning.
        // VIOLATION: Hardcoded infrastructure dependency inside the logic. [cite: 1]
        try (HttpClient client = HttpClient.newHttpClient()) {
            
            double currentPrice = fetchBitcoinPrice(client);

            return btcAmount * currentPrice;

        } catch (Exception e) {
            // VIOLATION: Infrastructure failures bleed directly into domain logic. [cite: 1]
            // Final fallback: Still specific to the state of the portfolio calculation.
            throw new IllegalStateException("Portfolio calculation failed due to infrastructure error.", e);
        }
    }

    private double fetchBitcoinPrice(HttpClient client) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"))
                .header("User-Agent", "Java App")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // VIOLATION: Tight coupling to a specific 3rd party JSON structure. [cite: 1]
            String json = response.body();
            String priceString = json.split("\"usd\":")[1].replace("}}", "").trim();
            return Double.parseDouble(priceString);

        } catch (IOException e) {
            // FIX 2: Using UncheckedIOException instead of generic RuntimeException.
            throw new UncheckedIOException("Network failure during price retrieval", e);
        } catch (InterruptedException e) {
            // FIX 3: Re-interrupting the thread to satisfy the InterruptedException rule.
            Thread.currentThread().interrupt();
            // FIX 4: Using IllegalStateException as a more specific unchecked exception.
            throw new IllegalStateException("Thread interrupted during API call", e);
        }
    }
}