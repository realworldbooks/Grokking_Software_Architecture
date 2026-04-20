using System;
using System.Reactive.Linq;
using Amazon.Lambda.APIGatewayEvents;
using Amazon.Lambda.S3Events;
using Chapter09.ServerlessFunctions.Infrastructure.Aws;

namespace Chapter09.ServerlessFunctions.Handlers;

public class AwsLambdaHandler
{
    /// <summary>
    /// CLOUD 1: AWS LAMBDA (The Imperative Island - Reactive)
    /// 
    /// THE ARCHITECTURAL LESSON: 
    /// Even when using modern Observables, AWS remains "Infrastructure-Heavy." 
    /// Because the platform only hands you metadata, your reactive pipeline 
    /// must contain the "Plumbing" to go fetch the actual data bytes.
    /// </summary>
    /// <param name="eventStream">A stream of proprietary AWS S3 events.</param>
    /// <returns>A stream emitting the AWS-compliant response object.</returns>
    public IObservable<APIGatewayProxyResponse> Handle(IObservable<MockS3Event> eventStream)
    {
        return eventStream.Select(s3Event => 
        {
            /**
             * TEACHING NOTE:
             * Notice the 'Select' operator (the Rx equivalent of Map). We are projecting 
             * AWS-specific metadata into our logic. Because AWS only gives us the 
             * file's "Key", a real implementation would need to chain an SDK call 
             * (e.g. .SelectMany(evt => _s3Client.GetStream$(...))) here. 
             * The "Island" architecture forces the developer to bridge the gap 
             * between "Event" and "Data" manually within the logic.
             */
            string fileName = s3Event.Records[0].S3.Object.Key;
            Console.WriteLine($"      [AWS Lambda] Reactive stream observed: {fileName}");
            Console.WriteLine($"      [AWS Lambda] Plumbing: Projecting SDK fetch into the pipe...");

            return new APIGatewayProxyResponse { 
                StatusCode = 200, 
                Body = "AWS processed reactive stream" 
            };
        });
    }
}