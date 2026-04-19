using System;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;
using Polly;
using Polly.Retry;
using Chapter10.Resilient.Core.Ports;

namespace Chapter10.Resilient.Infrastructure.Adapters;

/// <summary>
/// THE INFRASTRUCTURE ADAPTER (The Implementation):
///
/// This class encapsulates the Physical Resource Policy for the Zebra vendor.
/// It uses the Polly library to implement a declarative "Retry Shield."
///
/// ARCHITECTURAL CRITIQUE:
/// 1. OBSERVABILITY: Following the JavaScript standard, we now log every 
/// failure and the specific backoff duration. This prevents "Silent Failures" 
/// and makes the system's struggle visible to the operator.
/// 2. FAIL-FAST: By using the 'onRetry' hook, we can detect when we are 
/// about to hit the limit and ensure the final failure is explicitly marked 
/// as 'Exhausted' before the Orchestrator takes over.
/// 3. Physical Resource Policy for the Zebra vendor.
/// By moving our SLA (Service Level Agreement) into named constants, we 
/// transform hidden magic numbers into a documented, tunable boundary.
/// The Core Application remains pure because the Polly retry policy is 
/// physically locked inside this adapter.
/// </summary>
public class ZebraPaymentAdapter : IPaymentGateway
{
    private const int TotalRequestTimeoutSec = 10;
    private const int MaxRetryAttempts = 5;
    private const double InitialDelayMs = 2000;
    private const double MaxDelayMs = 10000;
    private const int BackoffFactor = 2;

    private readonly HttpClient _httpClient;
    private readonly AsyncRetryPolicy<bool> _retryPolicy;

    public ZebraPaymentAdapter(string baseUrl)
    {
        _httpClient = new HttpClient 
        { 
            BaseAddress = new Uri(baseUrl),
            Timeout = TimeSpan.FromSeconds(TotalRequestTimeoutSec)
        };

        // THE SHIELD (Declarative Policy)
        _retryPolicy = Policy<bool>
            .Handle<HttpRequestException>()
            .Or<TaskCanceledException>()
            .WaitAndRetryAsync(
                retryCount: MaxRetryAttempts,
                sleepDurationProvider: retryAttempt => {
                    // Exponential Backoff calculation
                    var backoff = InitialDelayMs * Math.Pow(BackoffFactor, retryAttempt - 1);
                    return TimeSpan.FromMilliseconds(Math.Min(backoff, MaxDelayMs));
                },
                onRetryAsync: async (outcome, timespan, retryCount, context) => {
                    // LOG THE FAILURE
                    Console.WriteLine($"      [Retry Shield] Attempt {retryCount} failed: {outcome.Exception?.Message ?? "Timeout"}.");

                    // If this is our last permitted retry, log the exhaustion
                    if (retryCount >= MaxRetryAttempts)
                    {
                        Console.WriteLine($"      [Retry Shield] MAX_RETRIES ({MaxRetryAttempts}) reached. Exhausted.");
                    }
                    else
                    {
                        Console.WriteLine($"      [Retry Shield] Backing off {timespan.TotalMilliseconds}ms...");
                    }
                    
                    await Task.CompletedTask;
                }
            );
    }

    public async Task<bool> ChargeAsync(decimal amount, string orderId, string idempotencyKey)
    {
        return await _retryPolicy.ExecuteAsync(async () =>
        {
            Console.WriteLine($"      [Zebra Adapter] Attempting Zebra Charge for {orderId}...");

            _httpClient.DefaultRequestHeaders.Clear();
            _httpClient.DefaultRequestHeaders.Add("Idempotency-Key", idempotencyKey);

            // SIMULATION: Throwing an error to trigger the shield as seen in your JS example
            throw new HttpRequestException("Zebra API: Gateway Timeout (504)");
            
            /* // Real implementation would look like this:
            var response = await _httpClient.PostAsJsonAsync("/charge", new { amount, order_id = orderId });
            response.EnsureSuccessStatusCode();
            return true;
            */
        });
    }
}