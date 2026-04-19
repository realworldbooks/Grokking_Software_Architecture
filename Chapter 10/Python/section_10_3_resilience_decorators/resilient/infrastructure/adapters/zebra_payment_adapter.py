import requests
import math
from tenacity import retry, stop_after_attempt, wait_exponential, after_log, before_sleep_log
import logging
from ...core.ports.payment_gateway import PaymentGateway

# Configure a minimalist logger for the Retry Shield
logger = logging.getLogger(__name__)

class ZebraPaymentAdapter(PaymentGateway):
    """
    THE INFRASTRUCTURE ADAPTER (The Implementation):
    
    ARCHITECTURAL CRITIQUE:
    1. OBSERVABILITY: We log every failure and the specific 
       backoff duration. This prevents "Silent Failures" 
       and makes the system's struggle visible to the operator.
    * 2. FAIL-FAST: The retry policy is encapsulated here. When 
       MAX_RETRY_ATTEMPTS is reached, tenacity reraises the last error 
       instantly, allowing the Orchestrator to pivot to Plan B.
    """

    # --- THE PHYSICAL POLICY CONSTANTS (The SLA) ---
    CONNECT_TIMEOUT_SEC = 2
    READ_TIMEOUT_SEC = 8
    MAX_RETRY_ATTEMPTS = 5
    BACKOFF_MIN_SEC = 2
    BACKOFF_MAX_SEC = 10
    BACKOFF_FACTOR = 2

    def __init__(self, base_url: str = "https://api.zebra.com"):
        self.base_url = base_url

    def _log_retry(self, retry_state):
        """Custom callback to match the JS 'Senior Architect' log style."""
        attempt_number = retry_state.attempt_number
        error_msg = retry_state.outcome.exception()
        
        print(f"      [Retry Shield] Attempt {attempt_number} failed: {error_msg}.")
        
        if attempt_number >= self.MAX_RETRY_ATTEMPTS:
            print(f"      [Retry Shield] MAX_RETRIES ({self.MAX_RETRY_ATTEMPTS}) reached. Exhausted.")
        else:
            # Calculate next backoff for logging parity
            delay = min(self.BACKOFF_MIN_SEC * (self.BACKOFF_FACTOR ** (attempt_number - 1)), self.BACKOFF_MAX_SEC)
            print(f"      [Retry Shield] Backing off {int(delay * 1000)}ms...")

    # THE RESILIENCE SHIELD
    @retry(
        stop=stop_after_attempt(MAX_RETRY_ATTEMPTS),
        wait=wait_exponential(multiplier=1, min=BACKOFF_MIN_SEC, max=BACKOFF_MAX_SEC),
        # Attach our custom logging callback
        after=lambda retry_state: None, # Placeholder for standard tenacity logging
        before_sleep=lambda retry_state: ZebraPaymentAdapter._log_retry_callback(retry_state),
        reraise=True
    )
    def charge(self, amount: float, order_id: str, idempotency_key: str) -> bool:
        print(f"      [Infrastructure Adapter] Attempting Zebra Charge for {order_id}...")
        
        # SIMULATION: To trigger the shield as seen in your JS examples
        # raise requests.exceptions.RequestException("Zebra API: Gateway Timeout (504)")
        
        response = requests.post(
            f"{self.base_url}/charge",
            json={"amount": amount, "order_id": order_id},
            headers={"Idempotency-Key": idempotency_key},
            timeout=(self.CONNECT_TIMEOUT_SEC, self.READ_TIMEOUT_SEC)
        )
        response.raise_for_status()
        return True

    @staticmethod
    def _log_retry_callback(retry_state):
        """Helper to route tenacity state to our formatted print statements."""
        # Accessing constants via the class reference
        adapter = ZebraPaymentAdapter
        attempt = retry_state.attempt_number
        ex = retry_state.outcome.exception()
        
        print(f"      [Retry Shield] Attempt {attempt} failed: {ex}.")
        
        if attempt >= adapter.MAX_RETRY_ATTEMPTS:
            print(f"      [Retry Shield] MAX_RETRIES ({adapter.MAX_RETRY_ATTEMPTS}) reached. Exhausted.")
        else:
            # Tenacity 'next_action' provides the sleep duration
            delay_ms = int(retry_state.next_action.sleep * 1000)
            print(f"      [Retry Shield] Backing off {delay_ms}ms...")