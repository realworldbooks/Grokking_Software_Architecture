using System;
using System.Threading.Tasks;

namespace Chapter12.Section_12_7_RetryStorm;

/// <summary>
/// Simulates <c>http://payment-api/charge</c>.
/// When <c>IsHealthy</c> is False, the API takes 300 ms to respond and
/// returns false — mirroring the book's "minor 2-second database delay".
/// </summary>
public class PaymentApiClient
{
    public bool IsHealthy { get; set; } = true;
    public int RequestCount { get; private set; }

    public async Task<bool> ChargeAsync(int id)
    {
        RequestCount++;
        await Task.Delay(IsHealthy ? 5 : 300);
        return IsHealthy;
    }
}

/// <summary>
/// WARNING: This is the DANGEROUS pattern from Listing 12.3.
/// It fires retries immediately with zero spacing. If many users hit
/// this simultaneously, the retries synchronize into a Retry Storm.
/// </summary>
public class NaiveRetryOrderService
{
    private readonly PaymentApiClient _api;

    public NaiveRetryOrderService(PaymentApiClient api) { _api = api; }

    public async Task<bool> ProcessPaymentAsync(int orderId)
    {
        int retry = 0;
        bool ok = false;
        while (!ok && retry < 3)
        {
            try
            {
                if (!await _api.ChargeAsync(orderId)) throw new Exception("Timeout.");
                ok = true;
            }
            catch
            {
                retry++;
                Console.WriteLine($"    [Order:{orderId}] Payment timeout. Firing retry attempt {retry}...");
            }
        }
        return ok;
    }
}

/// <summary>
/// A minimal circuit breaker (Chapter 10 shock absorber).
/// States: CLOSED (passing traffic) -> OPEN (failing fast) -> HALF_OPEN (probing recovery).
/// </summary>
public class CircuitBreaker
{
    private readonly int _failureThreshold;
    private readonly TimeSpan _openTimeout;
    private int _failureCount;
    private bool _isOpen;
    private DateTime _openedAt;

    public CircuitBreaker(int failureThreshold = 3, TimeSpan? openTimeout = null)
    {
        _failureThreshold = failureThreshold;
        _openTimeout = openTimeout ?? TimeSpan.FromSeconds(2);
    }

    public bool IsOpen
    {
        get
        {
            if (_isOpen && DateTime.UtcNow - _openedAt >= _openTimeout)
            {
                _isOpen = false;
                _failureCount = 0;
            }
            return _isOpen;
        }
    }

    public void RecordSuccess()
    {
        _failureCount = 0;
        _isOpen = false;
    }

    public void RecordFailure()
    {
        _failureCount++;
        if (_failureCount >= _failureThreshold)
        {
            _isOpen = true;
            _openedAt = DateTime.UtcNow;
        }
    }
}

/// <summary>
/// The FIXED service: exponential backoff + jitter + circuit breaker.
/// This is the architectural "shock absorber" the book demands. It
/// gives the overwhelmed downstream system the necessary breathing
/// room to recover.
/// </summary>
public class ResilientPaymentService
{
    private readonly PaymentApiClient _api;
    private readonly int _maxRetries;
    private readonly CircuitBreaker _breaker;
    private static readonly Random _rng = new();

    public int RetriesUsed { get; private set; }
    public int FastFailures { get; private set; }

    public ResilientPaymentService(PaymentApiClient api, int maxRetries = 3)
    {
        _api = api;
        _maxRetries = maxRetries;
        _breaker = new CircuitBreaker(failureThreshold: 3, openTimeout: TimeSpan.FromSeconds(2));
    }

    private async Task SleepBackoffAsync(int attempt)
    {
        // Exponential backoff: 100ms, 200ms, 400ms... plus jitter.
        double baseDelay = 100 * Math.Pow(2, attempt);
        double jitter = _rng.NextDouble() * baseDelay;
        await Task.Delay(TimeSpan.FromMilliseconds(baseDelay + jitter));
    }

    public async Task<bool> ProcessPaymentAsync(int orderId)
    {
        if (_breaker.IsOpen)
        {
            // FAIL FAST — the breaker has tripped. No request hits the wire.
            FastFailures++;
            return false;
        }

        int attempt = 0;
        while (attempt < _maxRetries)
        {
            bool ok = await _api.ChargeAsync(orderId);
            if (ok)
            {
                _breaker.RecordSuccess();
                return true;
            }

            // Failure — record it and wait with backoff + jitter.
            _breaker.RecordFailure();
            attempt++;
            RetriesUsed++;
            await SleepBackoffAsync(attempt);
        }
        return false;
    }
}

/// <summary>
/// Runs the Retry Storm (Listing 12.3) and the architectural fix.
/// </summary>
public static class Demo
{
    public static async Task RunAsync()
    {
        Console.WriteLine("\n=== Section 12.7.3: The Retry Storm (C#) ===");
        Console.WriteLine("THE SETUP: The Gateway calls an Order Service, which calls a");
        Console.WriteLine("Payment API. A minor database delay (2 seconds in the book;");
        Console.WriteLine("compressed to 300ms here so the demo stays snappy) hits the API.");
        Console.WriteLine("THE BEER GAME: Just like the panicked Wholesaler, the Order Service");
        Console.WriteLine("assumes the request was lost and triggers automated HTTP Retries.\n");

        // ------------------------------------------------------------------
        // PART 1: THE NAIVE LOOP (Listing 12.3) — Retry Storm
        // ------------------------------------------------------------------
        Console.WriteLine("--- PART 1: THE NAIVE LOOP (Listing 12.3) — NO backoff, NO jitter ---\n");

        var api = new PaymentApiClient();
        var naiveService = new NaiveRetryOrderService(api);
        api.IsHealthy = false;
        Console.WriteLine("  [Load] 3 users hit the Order Service simultaneously...\n");

        await Task.WhenAll(
            naiveService.ProcessPaymentAsync(0),
            naiveService.ProcessPaymentAsync(1),
            naiveService.ProcessPaymentAsync(2)
        );

        Console.WriteLine($"\n  [Traffic] Payment API received {api.RequestCount} requests!");
        Console.WriteLine("  [Result] 3 users x 3 retries = 9 un-spaced, synchronous HTTP requests!");
        Console.WriteLine("  [Result] A tiny bit of latency at the edge just amplified into a");
        Console.WriteLine("  [Result] violent snap at the core — the Payment Database melts.\n");
        Console.WriteLine("  SCALE IT: With 1,000 users, the naive loop fires 3,000 requests");
        Console.WriteLine("  instantly. THIS is a Retry Storm — an accidental DDoS of your own");
        Console.WriteLine("  downstream partner.\n");

        // ------------------------------------------------------------------
        // PART 2: THE FIX — Exponential Backoff + Jitter + Circuit Breaker
        // ------------------------------------------------------------------
        Console.WriteLine("--- PART 2: THE FIX (Chapter 10 shock absorbers) ---");
        Console.WriteLine("  Exponential Backoff + Jitter + Circuit Breaker\n");

        var api2 = new PaymentApiClient();
        var resilientService = new ResilientPaymentService(api2);
        api2.IsHealthy = false;
        Console.WriteLine("  [Load] The SAME 3 users hit the Order Service...\n");

        await Task.WhenAll(
            resilientService.ProcessPaymentAsync(0),
            resilientService.ProcessPaymentAsync(1),
            resilientService.ProcessPaymentAsync(2)
        );

        Console.WriteLine($"\n  [Traffic] Payment API received {api2.RequestCount} requests.");
        Console.WriteLine($"  [Retries] Total retries used: {resilientService.RetriesUsed}");
        Console.WriteLine($"  [Breaker] Fast-fail rejections (no network call): {resilientService.FastFailures}");
        Console.WriteLine("  [Result] Each retry waited progressively longer (100ms, 200ms, 400ms");
        Console.WriteLine("  [Result] + random jitter), giving the API breathing room to recover.");
        Console.WriteLine();
        Console.WriteLine("  [Note] The circuit breaker trips after 3 CONSECUTIVE failures.");
        Console.WriteLine("  [Note] In this concurrent demo, all 3 users pass the breaker before");
        Console.WriteLine("  [Note] it opens. In a real system, the breaker would then fail fast");
        Console.WriteLine("  [Note] for ALL subsequent requests until the timeout expires.\n");

        Console.WriteLine("=".PadRight(72, '='));
        Console.WriteLine("ARCHITECTURAL LESSON: DAMPEN THE BULLWHIP EFFECT");
        Console.WriteLine("-".PadRight(72, '-'));
        Console.WriteLine("THE PROBLEM: The naive while loop with no backoff amplifies latency");
        Console.WriteLine("into a self-inflicted DDoS. The Bullwhip Effect, in code.");
        Console.WriteLine();
        Console.WriteLine("THE SHOCK ABSORBERS (from Chapter 10):");
        Console.WriteLine("  1. CIRCUIT BREAKER: Instantly break out and 'fail fast' when a");
        Console.WriteLine("     downstream service is struggling, preventing cascading failure.");
        Console.WriteLine("  2. EXPONENTIAL BACKOFF: Wait progressively longer between retries,");
        Console.WriteLine("     giving the overwhelmed system 'breathing room' to recover.");
        Console.WriteLine("  3. JITTER: Randomize the retry intervals so requests never");
        Console.WriteLine("     synchronize into a thundering herd.");
        Console.WriteLine();
        Console.WriteLine("THE LITMUS TEST: 'No retry loop without a circuit breaker.'");
        Console.WriteLine("A tiny bit of latency should NEVER be allowed to become a DDoS.");
        Console.WriteLine("=".PadRight(72, '=') + "\n");
    }
}
