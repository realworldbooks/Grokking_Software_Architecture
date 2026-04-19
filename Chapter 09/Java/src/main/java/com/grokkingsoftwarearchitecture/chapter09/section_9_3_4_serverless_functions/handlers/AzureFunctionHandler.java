package com.grokking.chapter09.section_9_3_4_serverless_functions.handlers;

import com.grokking.chapter09.section_9_3_4_serverless_functions.infrastructure.azure.MockAzureContext;
import io.reactivex.rxjava3.core.Observable;

public class AzureFunctionHandler {

    /**
     * CLOUD 2: AZURE FUNCTIONS (The Declarative App - Reactive)
     *
     * THE ARCHITECTURAL LESSON: 
     * Azure uses "Bindings" to abstract network plumbing. Your code doesn't 
     * "fetch" data; it "receives" data. However, notice how the platform 
     * "Owns" your function's signature.
     *
     * @param context - The proprietary mock Azure context.
     * @return An Observable emitting the result of the cloud operation.
     */
    public Observable<String> execute(MockAzureContext context) {
        return context.asObservable().map(ctx -> {
            /* * TEACHING NOTE:
             * Notice the difference from AWS. We don't have to map a 'key' 
             * to a manual SDK call because the data is already here as an 
             * InputStream. But look at the dependency: we are locked into 
             * the 'MockAzureContext'. This "Platform Contamination" makes 
             * it impossible to test this reactive pipe in isolation without 
             * mocking the entire Azure runtime environment. The infrastructure 
             * has leaked into the very heart of the stream logic.
             */
            String fileName = ctx.getFileName();
            ctx.log("Stream observed for file: " + fileName);
            ctx.log("Declarative logic: Processing image resize...");

            return "Azure reactive processed " + fileName;
        });
    }
}