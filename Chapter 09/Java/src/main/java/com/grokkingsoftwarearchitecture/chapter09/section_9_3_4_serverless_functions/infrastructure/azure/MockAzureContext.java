package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.azure;

import io.reactivex.rxjava3.core.Observable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * THE AZURE INFRASTRUCTURE CONTRACT (Reactive Context):
 *
 * DESIGN NOTE:
 * Azure Functions in Java inject an 'ExecutionContext' and specific 'Blob' bindings. 
 * In this reactive model, we treat the context and the pre-downloaded InputStream 
 * as an Observable stream.
 *
 * ARCHITECTURAL CRITIQUE:
 * This is a "Signature Leak." While Azure is convenient because it handles the 
 * network plumbing (pre-downloading the blob) for you, it dictates that your 
 * code must interact with their proprietary logging and response objects. 
 * In a reactive flow, this means your pipeline is side-effect heavy because 
 * you are often forced to interact with this injected state to return a value. 
 * You have traded architectural control for vendor convenience.
 */
public class MockAzureContext {
    private final String fileName;
    private final byte[] blobContent;
    
    public MockAzureContext(String fileName, byte[] blobContent) {
        this.fileName = fileName;
        this.blobContent = blobContent;
    }

    /**
     * Returns the execution context as a Reactive Stream.
     */
    public Observable<MockAzureContext> asObservable() {
        return Observable.just(this);
    }

    public String getFileName() { return fileName; }
    
    public InputStream getBlobStream() {
        return new ByteArrayInputStream(blobContent);
    }

    public void log(String message) {
        System.out.println("      [Azure Log] " + message);
    }
}