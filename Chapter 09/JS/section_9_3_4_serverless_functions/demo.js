import { MockS3Event, MockLambdaContext } from './infrastructure/aws/mockS3Event.js';
import { MockAzureContext } from './infrastructure/azure/mockContext.js';
import { MockRequest } from './infrastructure/web/mockWebRequest.js';
import { handleRequest$ as awsHandler$ } from './handlers/awsLambdaHandler.js';
import { execute$ as azureHandler$ } from './handlers/azureFunctionHandler.js';
import { handler$ as supabaseHandler$ } from './handlers/supabaseEdgeHandler.js';

export class Demo {
    static run() {
        console.log("\n=== Section 9.3.4: Serverless Functions (Reactive Vendor Comparison) ===");

        // 1. AWS SIMULATION (Imperative)
        console.log("--- 1. AWS Lambda Simulator (Imperative Stream) ---");
        const event$ = MockS3Event.create$("user-images", "reactive_aws.jpg");
        awsHandler$(event$, new MockLambdaContext()).subscribe(res => {
            console.log(`  [AWS Result] Emitted: ${res.body}\n`);
        });

        // 2. AZURE SIMULATION (Declarative)
        console.log("--- 2. Azure Functions Simulator (Declarative Stream) ---");
        const azureCtx = new MockAzureContext("reactive_azure.png", Buffer.from("fake_bytes"));
        azureHandler$(azureCtx).subscribe(res => {
            console.log(`  [Azure Result] Emitted: ${res.body}\n`);
        });

        // 3. SUPABASE SIMULATION (Standard)
        console.log("--- 3. Supabase Edge Simulator (Standard Web Stream) ---");
        const mockReq = new MockRequest({ record: { name: "reactive_edge.png" } });
        supabaseHandler$(mockReq).subscribe(res => {
            console.log(`  [Supabase Result] Emitted: ${res.body}\n`);
        });

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        console.log("=".repeat(60));
        console.log("ARCHITECTURAL VERDICT:");
        console.log("-".repeat(60));
        console.log("AWS: Highest control, but your code is an 'Island' tied to SDKs.");
        console.log("AZURE: Highest convenience, but the platform 'Owns' your signature.");
        console.log("SUPABASE: Highest portability, using native Web Standards (Fetch).");
        console.log("\nREALITY CHECK: Even though the business logic was identical,");
        console.log("the infrastructure 'Leaked' into all three implementations.");
        console.log("=" + "=".repeat(59));
    }
}