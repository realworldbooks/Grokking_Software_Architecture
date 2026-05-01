package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * INFRASTRUCTURE LAYER (Adapter)
 * * ARCHITECTURE NOTE:
 * This class translates the external CoinGecko API into the 
 * PriceProviderPort interface required by our core domain.
 */
public class CoinGeckoAdapter implements PriceProviderPort {

    @Override
    public double getBitcoinPrice() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            // 1. DEFENSIVE CHECK: Prevent the ArrayIndexOutOfBoundsException
            if (response.statusCode() != 200 || !body.contains("\"usd\":")) {
                throw new RuntimeException("API Rate Limit or Invalid Response: " + body);
            }

            // 2. MANUAL PARSING: Extract the price safely now that we know the key exists
            String priceString = body.split("\"usd\":")[1].split("}")[0].trim();
            return Double.parseDouble(priceString);

        } catch (Exception e) {
            // 3. EXCEPTION TRANSLATION
            // We catch specific infrastructure errors (Network, Parsing, IO) 
            // and throw a generic exception so the Domain layer doesn't 
            // get polluted with java.net or java.io dependencies.
            throw new RuntimeException("Failed to fetch price from CoinGecko: " + e.getMessage());
        }
    }
}