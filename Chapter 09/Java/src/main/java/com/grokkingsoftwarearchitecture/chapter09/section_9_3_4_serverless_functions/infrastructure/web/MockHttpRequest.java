package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.web;

import io.reactivex.rxjava3.core.Observable;

/**
 * THE WEB STANDARDS CONTRACT (Standard Reactive HTTP Request):
 *
 * DESIGN NOTE:
 * Supabase Edge Functions and modern Webhooks use standard HTTP protocols. 
 * In this reactive model, we treat the Request body itself as a stream.
 *
 * ARCHITECTURAL CRITIQUE:
 * This is the "Clarity Peak." Because Webhooks interact with your code using 
 * the "Language of the Web" (HTTP/JSON) rather than "Vendor SDKs," this 
 * infrastructure is the most reusable. By wrapping the payload in a standard 
 * Request object, we demonstrate that our handler isn't a "Cloud Function"—it's 
 * a standard web endpoint. This represents the ultimate level of decoupling 
 * on the Compute Spectrum.
 */
public class MockHttpRequest {
    private final MockWebhookPayload body;

    public MockHttpRequest(MockWebhookPayload body) {
        this.body = body;
    }

    /**
     * Returns the request body as a Reactive Observable stream.
     */
    public Observable<MockWebhookPayload> getBodyStream() {
        return Observable.just(this.body);
    }
}