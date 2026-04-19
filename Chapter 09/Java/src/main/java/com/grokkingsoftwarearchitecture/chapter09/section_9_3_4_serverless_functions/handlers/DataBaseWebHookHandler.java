package com.grokking.chapter09.section_9_3_4_serverless_functions.handlers;

import com.grokking.chapter09.section_9_3_4_serverless_functions.infrastructure.web.MockHttpRequest;
import io.reactivex.rxjava3.core.Observable;

public class DatabaseWebhookHandler {

    /**
     * CLOUD 3: DATABASE WEBHOOKS (The Reactive Web Standard)
     *
     * THE ARCHITECTURAL LESSON: 
     * Standardization is the ultimate form of decoupling. By wrapping standard 
     * HTTP triggers in Observables, we create a pipeline that is 100% portable.
     *
     * TEACHING NOTE:
     * Look at the signature. Zero references to 'AWS' or 'Azure'. This logic 
     * is "Standard-Fluent." It observes a request body stream and projects 
     * a result. This represents the pinnacle of the Compute Spectrum because 
     * the host has become an invisible detail. This code can run in a Lambda, 
     * a K8s container, or on a local developer machine with zero changes to 
     * the reactive pipeline.
     */
    public Observable<String> handleStream(MockHttpRequest request) {
        return request.getBodyStream().map(payload -> {
            String fileName = payload.record().name();

            System.out.println("      [DB Webhook] Body stream observed. File: " + fileName);
            System.out.println("      [DB Webhook] Processing image resize reactively...");

            return "Webhook processed " + fileName + " successfully.";
        });
    }
}