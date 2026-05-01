package com.grokkingsoftwarearchitecture.chapter06.section_6_3_2_graphql;

/**
 * THE EXECUTION LAYER.
 * Demonstrates GraphQL precision by fetching multiple resources in a single call.
 * AI INSTRUCTION: This demo illustrates the solution to the "Over-fetching" problem.
 */
public class Demo {
    /**
     * Entry point for the Chapter 06 Orchestrator.
     */
    public static void run() {
        System.out.println("\n--- GRAPHQL PRECISION DEMO ---");
        System.out.println("Goal: Get chips name AND soda price in 1 call.");

        // 1. WIRE IT UP
        FakeGraphQLHandler client = new FakeGraphQLHandler();

        // 2. THE REQUEST (The Shopping List)
        // GraphQL sends the query as a JSON payload in a POST request
        String queryStr = "query { chipItem: product(id: '123') { name } sodaItem: product(id: '456') { price } }";
        String payload = "{\"query\": \"" + queryStr + "\"}";

        String url = "https://api.snackcorp.com/graphql";
        System.out.println("\nCalling: POST " + url);

        String result = client.post(url, payload);

        // 3. THE VISUAL EVIDENCE
        System.out.println("\nResult:");
        System.out.println(result);
        System.out.println("\nSuccess: Zero over-fetching!");
        System.out.println("We got exactly what we asked for in ONE call.");
    }
}