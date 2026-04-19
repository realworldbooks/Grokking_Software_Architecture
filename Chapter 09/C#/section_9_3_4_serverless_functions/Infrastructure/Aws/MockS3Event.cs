using System;
using System.Collections.Generic;
using System.Reactive.Linq;

namespace Chapter09.Section3_ServerlessFunctions.Infrastructure.Aws;

/// <summary>
/// THE AWS INFRASTRUCTURE CONTRACT (Reactive S3 Event):
/// 
/// DESIGN NOTE:
/// This record simulates the proprietary, strongly-typed object tree provided by the 
/// 'Amazon.Lambda.S3Events' NuGet package, but modeled as a Reactive Stream.
/// 
/// ARCHITECTURAL CRITIQUE:
/// In a reactive architecture, we treat cloud triggers as an infinite stream of incoming 
/// telemetry. However, notice the deep nesting required to find a simple filename: 
/// Records[0].S3.Object.Key. This is a textbook "Abstraction Leak." By forcing your 
/// business logic to navigate this specific hierarchy, the vendor has effectively 
/// "leaked" their internal data model into your application code. Your resizer logic 
/// is no longer a pure function; it is a downstream dependent of Amazon's design.
/// </summary>
public record MockS3Event(List<MockS3EventRecord> Records);
public record MockS3EventRecord(MockS3Entity S3);
public record MockS3Entity(MockS3Bucket Bucket, MockS3Object Object);
public record MockS3Bucket(string Name);
public record MockS3Object(string Key);

public static class AwsStreamFactory
{
    /// <summary>
    /// Wraps a static AWS event into a Reactive Observable stream.
    /// </summary>
    public static IObservable<MockS3Event> CreateS3Stream(string bucket, string file)
    {
        return Observable.Return(new MockS3Event(new List<MockS3EventRecord>
        {
            new MockS3EventRecord(new MockS3Entity(new MockS3Bucket(bucket), new MockS3Object(file)))
        }));
    }
}