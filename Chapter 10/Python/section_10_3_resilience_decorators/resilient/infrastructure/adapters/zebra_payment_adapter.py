import requests
from tenacity import retry, stop_after_attempt, wait_exponential
from ...core.ports.payment_gateway import PaymentGateway

class ZebraPaymentAdapter(PaymentGateway):
    """
    THE INFRASTRUCTURE ADAPTER (The Implementation):
    
    ARCHITECTURAL CRITIQUE:
    Notice the imports. This file is allowed to import 'requests' and 'tenacity' 
    because it is an Infrastructure concern. However, it must also import 
    'PaymentGateway' from the Core to ensure it satisfies the Port's contract.
    The resilience 'Shield' (Tenacity) is physically locked inside this adapter.

    # SENIOR DESIGN NOTE:
    We define our failure policy as internal constants. This ensures 
    that Zebra's specific network requirements are encapsulated and 
    don't leak into a "Global Constants" file that unrelated services 
    might accidentally depend on.
    """

    def __init__(self, base_url: str):
        self.base_url = base_url

    # The 'SLA' (Service Level Agreement) for this specific vendor
    CONNECT_TIMEOUT_SEC = 2
    READ_TIMEOUT_SEC = 8
    MAX_RETRY_ATTEMPTS = 5
    BACKOFF_MIN_SEC = 2
    BACKOFF_MAX_SEC = 10

    # THE RESILIENCE SHIELD
    @retry(
        # RETRIES: Stop after 5 failed attempts
        stop=stop_after_attempt(MAX_RETRY_ATTEMPTS), 
        # EXPONENTIAL BACKOFF: 2s, 4s, 8s, 10s (capped at 10)
        wait=wait_exponential(multiplier=1, min=BACKOFF_MIN_SEC, max=BACKOFF_MAX_SEC),
        reraise=True
    )
    def charge(self, amount: float, order_id: str, idempotency_key: str) -> bool:
        print(f"      [Infrastructure Adapter] Attempting Zebra Charge for {order_id}...")
        # TIMEOUTS: (Connect Timeout, Read Timeout)
        # Prevents the "Hanging Thread" that leads to system-wide failure.
        response = requests.post(
            f"{self.base_url}/charge",
            json={"amount": amount, "order_id": order_id},
            headers={"Idempotency-Key": idempotency_key},
            timeout=(self.CONNECT_TIMEOUT_SEC, self.READ_TIMEOUT_SEC) 
        )
        response.raise_for_status()
        return True