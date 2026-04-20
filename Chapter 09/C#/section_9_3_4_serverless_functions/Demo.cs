using System;
using System.Reactive.Linq;
using Chapter09.ServerlessFunctions.Handlers;
using Chapter09.ServerlessFunctions.Infrastructure.Aws;
using Chapter09.ServerlessFunctions.Infrastructure.Azure;
using Chapter09.ServerlessFunctions.Infrastructure.Web;

namespace Chapter09.ServerlessFunctions;

public class Demo
{
    public static void Run()
    {
        Console.WriteLine("\n=== Section 9.3.4: Serverless Functions (Reactive Vendor Comparison) ===");
        Console.WriteLine("THE SETUP: We need to resize an image, but we want to see how different");
        Console.WriteLine("           Cloud Providers architect their serverless triggers.\n");

        // 1. AWS SIMULATION (Imperative)
        Console.WriteLine("--- 1. AWS Lambda Simulator (Imperative Stream) ---");
        var awsStream = AwsStreamFactory.CreateS3Stream("user-images", "rx_aws.png");
        var awsHandler = new AwsLambdaHandler();
        
        awsHandler.Handle(awsStream).Subscribe(res => 
            Console.WriteLine($"  [AWS Result] Emitted: {res.Body}\n"));

        // 2. AZURE SIMULATION (Declarative)
        Console.WriteLine("--- 2. Azure Functions Simulator (Declarative Stream) ---");
        var azureStream = AzureStreamFactory.CreateBlobStream(new byte[] { 0, 1, 2 });
        var azureHandler = new AzureFunctionHandler(); // Assumed implementation mirroring JS logic
        var azureLogger = new MockAzureLogger();
        
        // Azure handler returns a string, not an Observable. Handle it directly.
        azureStream.Subscribe(stream => {
            var res = azureHandler.Handle(stream, "rx_azure.png", azureLogger);
            Console.WriteLine($"  [Azure Result] Emitted: {res}\n");
});

        // 3. WEBHOOK SIMULATION (Standard)
        Console.WriteLine("--- 3. DB Webhook Simulator (Standard Web Stream) ---");

        var webhookPayload = new MockWebhookPayload("INSERT", "objects", new MockWebhookRecord("images", "rx_web.png"));
        var webStream = Observable.Return(new MockHttpRequest(webhookPayload));
        var webHandler = new DatabaseWebhookHandler();

        // Use SelectMany to bridge the Observable stream to the Handler
        webStream
        .SelectMany(request => webHandler.Handle(request))
        .Subscribe(res => Console.WriteLine($"  [Webhook Result] Emitted: {res}\n"));

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        Console.WriteLine("=".PadRight(60, '='));
        Console.WriteLine("ARCHITECTURAL VERDICT:");
        Console.WriteLine("-".PadRight(60, '-'));
        Console.WriteLine("AWS: Highest control, but your code is an 'Island' tied to SDKs.");
        Console.WriteLine("AZURE: Highest convenience, but the platform 'Owns' your signature.");
        Console.WriteLine("SUPABASE: Highest portability, using native Web Standards.");
        Console.WriteLine("\nREALITY CHECK: Even though the business logic was identical,");
        Console.WriteLine("the infrastructure 'Leaked' into all three implementations.");
        Console.WriteLine("=".PadRight(60, '='));
    }
}