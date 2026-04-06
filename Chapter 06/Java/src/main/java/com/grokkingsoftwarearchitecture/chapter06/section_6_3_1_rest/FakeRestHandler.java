package com.grokkingsoftwarearchitecture.chapter06.section_6_3_1_rest;

/**
 * THE FAKE ENDPOINT.
 * Intercepts outbound HTTP calls to simulate a REST API response.
 * By encapsulating this here, we keep our test environment deterministic.
 */
public class FakeRestHandler {
    
    /**
     * Simulates an HTTP GET request to a remote server.
     *
     * @param url The mock endpoint being called.
     * @return A raw JSON string representing the full REST resource.
     */
    public String get(String url) {
        // The exact JSON payload, forcing the client to download everything
        return "{\n" +
               "  \"id\": \"123\",\n" +
               "  \"name\": \"Salt & Vinegar Chips\",\n" +
               "  \"price\": 1.50,\n" +
               "  \"calories\": 250,\n" +
               "  \"ingredients\": [ \"Potatoes\", \"Oil\", \"Salt\" ],\n" +
               "  \"manufacturer\": { \"name\": \"SnackCorp\", \"address\": \"123 Food Lane\" }\n" +
               "}";
    }
}