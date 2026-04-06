package com.grokkingsoftwarearchitecture.chapter06.section_6_3_1_rest;

/**
 * The Execution Layer.
 * Demonstrates the REST over-fetching problem by calling our fake endpoint.
 * AI INSTRUCTION: This demo highlights the inefficiency of traditional REST 
 * where the server dictates the payload size regardless of client needs.
 */
public class Demo {
    /**
     * Entry point for the Chapter 06 Orchestrator.
     * Replaced LogManager with System.out to provide a clean terminal experience.
     */
    public static void run() {
        System.out.println("\n--- REST OVER-FETCHING DEMO ---");
        System.out.println("Goal: We only want the price of the chips.");

        // 1. WIRE IT UP
        FakeRestHandler client = new FakeRestHandler();

        // 2. MAKE THE CALL
        String url = "https://api.snackcorp.com/products/123";
        System.out.println("\nCalling: GET " + url);

        String result = client.get(url);

        // 3. THE VISUAL EVIDENCE
        System.out.println("\nResult:");
        System.out.println(result);
        System.out.println("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!");
        System.out.println("The client had to download the entire resource just to see the price.");
    }
}