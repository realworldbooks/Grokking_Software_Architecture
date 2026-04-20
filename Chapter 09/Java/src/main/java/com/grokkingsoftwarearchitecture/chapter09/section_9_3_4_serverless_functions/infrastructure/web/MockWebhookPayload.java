package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.web;

/**
 * THE INFRASTRUCTURE CONTRACT (Standard Webhook Payload):
 *
 * DESIGN NOTE:
 * This represents a platform-agnostic JSON payload. We name this 'Mock' 
 * to remain consistent with our cloud mocks, simulating data sent by 
 * platforms like Supabase.
 *
 * ARCHITECTURAL CRITIQUE:
 * This is a "Flat" and "Standard" contract. Because it relies on standard 
 * Java primitives rather than vendor-specific SDK classes, the 'Contract 
 * Coupling' is minimal. This represents high architectural stability; if 
 * you switch database providers, this file likely stays exactly the same.
 */
public record MockWebhookPayload(String type, String table, MockWebhookRecord record) {
    public record MockWebhookRecord(String bucketId, String name) {}
} 