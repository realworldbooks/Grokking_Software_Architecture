using System;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;
using Polly;
using Polly.Retry;
using Chapter10.Resilience.Core.Ports;

namespace Chapter10.Resilience.Infrastructure.Adapters;

/// <summary>
/// THE INFRASTRUCTURE ADAPTER (The Implementation):
/// 
/// ARCHITECTURAL CRITIQUE:
/// This class encapsulates the Physical Resource Policy for the Zebra vendor.
/// By moving our SLA (Service Level Agreement) into named constants, we 
/// transform hidden magic numbers into a documented, tunable boundary.
/// The Core Application remains pure because the Polly retry policy is 
/// physically locked inside this adapter.
/// </summary>
public class ZebraPaymentAdapter : IPaymentGateway
{
    // --- THE PHYSICAL POLICY CONSTANTS (The SLA) ---
    // Connect/Request timeout: The absolute "Escape Hatch" for the thread.
    private const int TotalRequestTimeoutSec = 10;
    
    // Retry Policy constants
    private const int MaxRetryAttempts = 5;
    private const int InitialDelaySec = 2;
    private const int BackoffPower = 2;

    private readonly HttpClient _httpClient;
    private readonly AsyncRetryPolicy<bool> _retryPolicy;

    public ZebraPaymentAdapter(string baseUrl)
    {
        // Physical Bulkheading via HttpClient timeout configuration.
        _httpClient = new HttpClient 
        { 
            BaseAddress = new Uri(baseUrl),
            Timeout = TimeSpan.FromSeconds(TotalRequestTimeoutSec)
        };

        // THE SHIELD (Declarative Policy via Polly)
        // #SENIOR NOTE: Notice how the wait time scales exponentially: 2, 4, 8, 16, 32s.
        _retryPolicy = Policy<bool>
            .Handle<HttpRequestException>()
            .Or<TaskCanceledException>() // Explicitly catches the timeout from #A
            .WaitAndRetryAsync(MaxRetryAttempts, retryAttempt => 
                TimeSpan.FromSeconds(Math.Pow(BackoffPower, retryAttempt))
            );
    }

    public async Task<bool> ChargeAsync(decimal amount, string orderId, string idempotencyKey)
    {
        return await _retryPolicy.ExecuteAsync(async () =>
        {
            Console.WriteLine($"      [Zebra Adapter] Attempting Zebra Charge for {orderId}...");

            // HEADERS (Infrastructure Concern)
            _httpClient.DefaultRequestHeaders.Clear();
            _httpClient.DefaultRequestHeaders.Add("Idempotency-Key", idempotencyKey);

            var response = await _httpClient.PostAsJsonAsync("/charge", new {
                amount = amount,
                order_id = orderId
            });

            // This triggers the Handle<HttpRequestException> in the policy
            response.EnsureSuccessStatusCode();
            return true;
        });
    }
}