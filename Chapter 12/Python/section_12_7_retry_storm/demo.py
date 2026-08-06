"""The Retry Storm — Listing 12.3 and the Architectural Fix.

DESIGN NOTE:
This scenario teaches the "Bullwhip Effect" applied to software. Imagine
your Gateway calls an Order Service, which calls a Payment API. If the
Payment API experiences a minor 2-second database delay, the Order Service
might assume the request was lost and automatically "re-order" by
triggering an HTTP Retry — just like the panicked Wholesaler in the
Beer Game.

Listing 12.3 shows the DANGEROUS naive retry loop:

    while (!success && retryCount < 3)
    {
        try
        {
            var response = await _httpClient.PostAsync(
                "http://payment-api/charge", order);
            response.EnsureSuccessStatusCode();
            success = true;
        }
        catch (HttpRequestException)
        {
            retryCount++;
            _logger.LogWarning($"Payment timeout. Firing retry attempt {retryCount}...");
        }
    }

If 1,000 users hit this code during a 2-second delay, the naive loop
instantly hammers the struggling Payment API with 3,000 un-spaced,
synchronous HTTP requests. We call this a RETRY STORM.

The FIX: the exact same "shock absorbers" from Chapter 10:
  1. Exponential Backoff — wait progressively longer between retries.
  2. Jitter — add a random component so retries don't synchronize.
  3. A Circuit Breaker — fail fast when the downstream service is
     struggling, preventing cascading failure.

We simulate 3 concurrent "users" (coroutines) to show how naive retries
amplify load by 3x, then show the same 3 users with backoff + jitter.
"""

import asyncio
import random
import time
from typing import Union

# ---------------------------------------------------------------------------
# The Downstream "Payment API"
# ---------------------------------------------------------------------------


class PaymentApiClient:
    """Simulates `http://payment-api/charge`.

    When `is_healthy` is False, the API takes 2000 ms to respond and
    returns 503 — mirroring the book's "minor 2-second database delay".
    """

    def __init__(self, healthy_delay_ms: int = 5, failure_delay_ms: int = 2000) -> None:
        self.healthy_delay_ms = healthy_delay_ms
        self.failure_delay_ms = failure_delay_ms
        self.is_healthy: bool = True
        self.request_count: int = 0

    async def charge(self, order_id: int) -> bool:
        """Returns True on success, False (503) on failure."""
        self.request_count += 1
        if not self.is_healthy:
            # The "2-second delay" — the Payment DB is struggling.
            await asyncio.sleep(self.failure_delay_ms / 1000.0)
            return False
        await asyncio.sleep(self.healthy_delay_ms / 1000.0)
        return True


# ---------------------------------------------------------------------------
# THE ANTI-PATTERN (Listing 12.3) — Naive Retry with NO backoff, NO jitter
# ---------------------------------------------------------------------------


class NaiveRetryOrderService:
    """WARNING: This is the DANGEROUS pattern from Listing 12.3.

    It fires retries immediately with zero spacing. If many users hit
    this simultaneously, the retries synchronize into a Retry Storm.
    """

    def __init__(self, payment_api: PaymentApiClient) -> None:
        self._payment_api = payment_api

    async def process_payment_async(self, order_id: int) -> bool:
        """The naive retry loop — retryCount < 3, no waiting."""
        retry_count = 0
        success = False

        while not success and retry_count < 3:  # #A: naive, dangerous loop
            try:
                ok = await self._payment_api.charge(order_id)
                if not ok:
                    raise RuntimeError("Payment timeout.")
                success = True
            except RuntimeError:
                retry_count += 1  # #B: DANGER — retrying immediately!
                print(
                    f"    [Order:{order_id}] Payment timeout. "
                    f"Firing retry attempt {retry_count}..."  # no delay!
                )

        return success


# ---------------------------------------------------------------------------
# THE FIX — Exponential Backoff + Jitter + Circuit Breaker
# ---------------------------------------------------------------------------


class CircuitBreaker:
    """A minimal circuit breaker (Chapter 10 shock absorber).

    States: CLOSED (passing traffic) -> OPEN (failing fast) -> HALF_OPEN
    (probing recovery).
    """

    def __init__(self, failure_threshold: int = 3, open_timeout_s: float = 5.0) -> None:
        self.failure_threshold = failure_threshold
        self.open_timeout_s = open_timeout_s
        self._failure_count = 0
        self._is_open = False
        self._opened_at = None

    @property
    def is_open(self) -> bool:
        if self._is_open and self._opened_at is not None:
            # Auto-transition to half-open after the timeout.
            if time.monotonic() - self._opened_at >= self.open_timeout_s:
                self._is_open = False
                self._failure_count = 0
        return self._is_open

    def record_success(self) -> None:
        self._failure_count = 0
        self._is_open = False
        self._opened_at = None

    def record_failure(self) -> None:
        self._failure_count += 1
        if self._failure_count >= self.failure_threshold:
            self._is_open = True
            self._opened_at = time.monotonic()


class ResilientPaymentService:
    """The FIXED service: exponential backoff + jitter + circuit breaker.

    This is the architectural "shock absorber" the book demands. It
    gives the overwhelmed downstream system the necessary breathing
    room to recover.
    """

    def __init__(self, payment_api: PaymentApiClient, max_retries: int = 3) -> None:
        self._payment_api = payment_api
        self._max_retries = max_retries
        self._breaker = CircuitBreaker(failure_threshold=3, open_timeout_s=2.0)
        self.retries_used = 0
        self.fast_failures = 0

    async def _sleep_backoff(self, attempt: int) -> None:
        """Exponential backoff: 100ms, 200ms, 400ms... plus jitter.

        base_delay = 100ms * 2^attempt   (exponential growth)
        jitter     = random 0..1 * base  (so retries don't synchronize)
        """
        base_delay = 0.1 * (2 ** attempt)
        jitter = random.uniform(0, base_delay)
        await asyncio.sleep(base_delay + jitter)

    async def process_payment_async(self, order_id: int) -> bool:
        if self._breaker.is_open:
            # FAIL FAST — the breaker has tripped. No request hits the wire.
            self.fast_failures += 1
            return False

        attempt = 0
        while attempt < self._max_retries:
            ok = await self._payment_api.charge(order_id)
            if ok:
                self._breaker.record_success()
                return True

            # Failure — record it and wait with backoff + jitter.
            self._breaker.record_failure()
            attempt += 1
            self.retries_used += 1
            await self._sleep_backoff(attempt)

        return False


# ---------------------------------------------------------------------------
# Simulation helper — run N "users" concurrently and count the traffic
# ---------------------------------------------------------------------------


async def simulate_users(
    service: Union[NaiveRetryOrderService, ResilientPaymentService],
    user_count: int,
) -> None:
    """Fires `user_count` concurrent payment attempts (one per user).

    Mirrors "1,000 active users hitting this code simultaneously..."
    """
    await asyncio.gather(*[service.process_payment_async(uid) for uid in range(user_count)])


class Demo:
    """Runs the Retry Storm (Listing 12.3) and the architectural fix."""

    @staticmethod
    async def run_async() -> None:
        print("\n=== Section 12.7.3: The Retry Storm (Python) ===")
        print("THE SETUP: The Gateway calls an Order Service, which calls a")
        print("Payment API. A minor database delay (2 seconds in the book;")
        print("compressed to 300ms here so the demo stays snappy) hits the API.")
        print("THE BEER GAME: Just like the panicked Wholesaler, the Order Service")
        print("assumes the request was lost and triggers automated HTTP Retries.\n")

        # ------------------------------------------------------------------
        # PART 1: THE NAIVE LOOP (Listing 12.3) — Retry Storm
        # ------------------------------------------------------------------
        print("--- PART 1: THE NAIVE LOOP (Listing 12.3) — NO backoff, NO jitter ---\n")

        payment_api = PaymentApiClient(failure_delay_ms=300)
        naive_service = NaiveRetryOrderService(payment_api)

        print("  [Payment API] Simulating a database delay (300ms)...")
        payment_api.is_healthy = False
        print("  [Load] 3 users hit the Order Service simultaneously...\n")

        await simulate_users(naive_service, user_count=3)

        print(f"\n  [Traffic] Payment API received {payment_api.request_count} requests!")
        print("  [Result] 3 users x 3 retries = 9 un-spaced, synchronous HTTP requests!")
        print("  [Result] A tiny bit of latency at the edge just amplified into a")
        print("  [Result] violent snap at the core — the Payment Database melts.\n")

        print("  SCALE IT: With 1,000 users, the naive loop fires 3,000 requests")
        print("  instantly. THIS is a Retry Storm — an accidental DDoS of your own")
        print("  downstream partner.\n")

        # ------------------------------------------------------------------
        # PART 2: THE FIX — Exponential Backoff + Jitter + Circuit Breaker
        # ------------------------------------------------------------------
        print("--- PART 2: THE FIX (Chapter 10 shock absorbers) ---")
        print("  Exponential Backoff + Jitter + Circuit Breaker\n")

        payment_api2 = PaymentApiClient(failure_delay_ms=300)
        resilient_service = ResilientPaymentService(payment_api2)

        print("  [Payment API] Simulating the SAME database delay (300ms)...")
        payment_api2.is_healthy = False
        print("  [Load] The SAME 3 users hit the Order Service...\n")

        await simulate_users(resilient_service, user_count=3)

        print(f"\n  [Traffic] Payment API received {payment_api2.request_count} requests.")
        print(f"  [Retries] Total retries used: {resilient_service.retries_used}")
        print(f"  [Breaker] Fast-fail rejections (no network call): {resilient_service.fast_failures}")
        print("  [Result] Each retry waited progressively longer (100ms, 200ms, 400ms")
        print("  [Result] + random jitter), giving the API breathing room to recover.")
        print()
        print("  [Note] The circuit breaker trips after 3 CONSECUTIVE failures.")
        print("  [Note] In this concurrent demo, all 3 users pass the breaker before")
        print("  [Note] it opens. In a real system, the breaker would then fail fast")
        print("  [Note] for ALL subsequent requests until the timeout expires.\n")

        print("=" * 72)
        print("ARCHITECTURAL LESSON: DAMPEN THE BULLWHIP EFFECT")
        print("-" * 72)
        print("THE PROBLEM: The naive while loop with no backoff amplifies latency")
        print("into a self-inflicted DDoS. The Bullwhip Effect, in code.")
        print()
        print("THE SHOCK ABSORBERS (from Chapter 10):")
        print("  1. CIRCUIT BREAKER: Instantly break out and 'fail fast' when a")
        print("     downstream service is struggling, preventing cascading failure.")
        print("  2. EXPONENTIAL BACKOFF: Wait progressively longer between retries,")
        print("     giving the overwhelmed system 'breathing room' to recover.")
        print("  3. JITTER: Randomize the retry intervals so requests never")
        print("     synchronize into a thundering herd.")
        print()
        print("THE LITMUS TEST: 'No retry loop without a circuit breaker.'")
        print("A tiny bit of latency should NEVER be allowed to become a DDoS.")
        print("=" * 72 + "\n")