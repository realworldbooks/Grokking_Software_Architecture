package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ADAPTER 2: The Real Production Adapter.
 * Encapsulates the chaotic outside world.
 */
public class CoinGeckoAdapter implements PriceProviderPort {
    @Override
    public double getBitcoinPrice() throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"))
                    .header("User-Agent", "Java App")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Simple string parsing to avoid Jackson/Gson dependencies for the demo
            String json = response.body();
            String priceString = json.split("\"usd\":")[1].replace("}}", "").trim();
            return Double.parseDouble(priceString);
        }
    }
}