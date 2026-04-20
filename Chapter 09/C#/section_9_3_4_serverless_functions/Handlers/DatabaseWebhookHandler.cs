using System;
using System.Reactive.Linq;
using Chapter09.ServerlessFunctions.Infrastructure.Web;

namespace Chapter09.ServerlessFunctions.Handlers;

public class DatabaseWebhookHandler
{
    /// <summary>
    /// CLOUD 3: DATABASE WEBHOOKS (The Reactive Web Standard)
    /// 
    /// THE ARCHITECTURAL LESSON: 
    /// Standardization is the ultimate form of decoupling. By wrapping standard 
    /// HTTP triggers in Observables, we create a pipeline that is 100% portable 
    /// across the entire Compute Spectrum.
    /// 
    /// TEACHING NOTE:
    /// Look at the method signature. There are zero references to 'AWS', 'Azure', 
    /// or proprietary NuGet packages. This logic is "Standard-Fluent." It observes 
    /// a request body stream and projects a result. This represents the pinnacle 
    /// of architectural maturity because the hosting provider has become an 
    /// invisible detail. This exact code can run on a Serverless function, a 
    /// Docker container, or a legacy on-premise server without changing a 
    /// single operator in the pipeline.
    /// </summary>
    /// <param name="request">The standard mock reactive request object.</param>
    /// <returns>A stream emitting the processing result string.</returns>
    public IObservable<string> Handle(MockHttpRequest request)
    {
        return request.GetBody().Select(payload => 
        {
            string fileName = payload.Record.Name;

            Console.WriteLine($"      [DB Webhook] Body stream observed. File: {fileName}");
            Console.WriteLine($"      [DB Webhook] Processing image resize reactively...");

            // In a real system, this string would be part of a 200 OK HTTP Response.
            return $"Webhook processed {fileName} successfully.";
        });
    }
}