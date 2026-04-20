package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions;

import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.handlers.*;
import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.azure.*;
import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.aws.*;
import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.web.*;
public class Demo {
    public static void run() {
        System.out.println("\n=== Section 9.3.4: Serverless Functions (Reactive Vendor Comparison) ===");

        // 1. AWS REACTIVE SIMULATION
        System.out.println("--- 1. AWS Lambda Simulator (Imperative Stream) ---");
        var eventStream = MockS3Event.createStream("user-images", "rx_java_aws.png");
        var awsHandler = new AwsLambdaHandler();

        awsHandler.handleStream(eventStream).subscribe(res -> 
            System.out.println("  [AWS Result] Emitted: " + res.body() + "\n")
        );

        // ---------------------------------------------------------
        // 2. AZURE REACTIVE SIMULATION (Declarative)
        // ---------------------------------------------------------
        System.out.println("\n--- 2. Azure Functions Simulator (Declarative Stream) ---");
        var azureCtx = new MockAzureContext("rx_java_azure.png", new byte[]{0, 1, 0});
        var azureHandler = new AzureFunctionHandler();

        azureHandler.execute(azureCtx).subscribe(res -> 
            System.out.println("  [Azure Result] Emitted: " + res + "\n")
        );

        // 3. SUPABASE REACTIVE SIMULATION
        System.out.println("--- 2. Supabase Webhook Simulator (Standard Web Stream) ---");
        var mockPayload = new MockWebhookPayload("INSERT", "objects", 
            new MockWebhookPayload.MockWebhookRecord("images", "rx_java_web.png"));
        var mockReq = new MockHttpRequest(mockPayload);
        var webHandler = new DatabaseWebhookHandler();

        webHandler.handleStream(mockReq).subscribe(res -> 
            System.out.println("  [Supabase Result] Emitted: " + res + "\n")
        );

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        System.out.println("=".repeat(60));
        System.out.println("ARCHITECTURAL VERDICT:");
        System.out.println("-".repeat(60));
        System.out.println("AWS: Highest control, but your code is an 'Island' tied to SDKs.");
        System.out.println("AZURE: Highest convenience, but the platform 'Owns' your signature.");
        System.out.println("SUPABASE: Highest portability, using native Web Standards (Fetch).");
        System.out.println("\nREALITY CHECK: Even though the business logic was identical,");
        System.out.println("the infrastructure 'Leaked' into all three implementations.");
        System.out.println("=".repeat(60));
    }
}