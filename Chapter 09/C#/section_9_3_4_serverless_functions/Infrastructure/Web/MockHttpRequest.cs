using System;
using System.Text.Json;
using System.Reactive.Linq;

namespace Chapter09.Section3_ServerlessFunctions.Infrastructure.Web;

/// <summary>
/// THE INFRASTRUCTURE WRAPPER (Standard HTTP Request):
/// 
/// TEACHING NOTE:
/// This class simulates a standard HTTP Request object (the kind you would find 
/// in ASP.NET Core). By wrapping the payload here, we demonstrate that 
/// Webhooks interact with your application logic using the "Language of the Web" 
/// (HTTP/JSON) rather than "Vendor-Specific Dialects." This makes the 
/// entry point into your system much more predictable and easier to test.
/// </summary>
public class MockHttpRequest
{
    private readonly string _jsonBody;

    public MockHttpRequest(MockWebhookPayload payload)
    {
        _jsonBody = JsonSerializer.Serialize(payload);
    }

    /// <summary>
    /// Returns the request body as a Reactive Observable Stream.
    /// 
    /// ARCHITECTURAL NOTE:
    /// Modeling the request body as a stream allows our logic to handle 
    /// data reactively. Even if the body is a single object, we treat it 
    /// as a stream to maintain a consistent 'Reactive Pipeline' across 
    /// all our cloud implementations.
    /// </summary>
    /// <returns>An IObservable emitting the deserialized payload.</returns>
    public IObservable<MockWebhookPayload> GetBody$()
    {
        var payload = JsonSerializer.Deserialize<MockWebhookPayload>(_jsonBody, 
            new JsonSerializerOptions { PropertyNameCaseInsensitive = true })!;
        return Observable.Return(payload);
    }
}