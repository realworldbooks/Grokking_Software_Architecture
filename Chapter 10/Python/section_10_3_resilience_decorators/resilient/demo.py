import os
from .core.application.checkout_orchestrator import CheckoutOrchestrator
from .infrastructure.adapters.flaky_payments_payment_adapter import FlakyPaymentsPaymentAdapter
from .infrastructure.adapters.local_queue_adapter import LocalQueueAdapter

class Demo:
    """
    THE RESILIENT COMPOSER (Composition Root):
    
    @description
    This class is the only place in the system allowed to have knowledge 
    of every layer. Its role is to perform Dependency Injection, plugging 
    physical infrastructure into the application's abstract ports.
    
    ARCHITECTURAL CRITIQUE:
    1. INVERSION OF CONTROL: The Orchestrator doesn't choose its tools; 
       the Demo hands them over. This allows us to swap SQLite for 
       RabbitMQ without changing the Core.
       
    2. PRODUCTION PARITY: In a real environment, this file would read 
       secrets from a Vault and URLs from Environment Variables, just 
       as we do here with os.getenv.
    """

    @staticmethod
    def run():
        print("\n=== Chapter 10.3: Resilience with Local Persistence ===")
        
        # ENVIRONMENT DECOUPLING
        # We fetch the vendor location from the environment, not hardcoded strings.
        api_url = os.getenv("PAYMENT_API_URL", "https://api.flakypayments.com")
        
        # ASSEMBLY: Constructing the physical adapters
        # We use a local disk path for our SQLite-backed message queue.
        payment_adapter = FlakyPaymentsPaymentAdapter(base_url=api_url)
        queue_adapter = LocalQueueAdapter(path="./payment_backlog")
        
        # INJECTION: Plugging the adapters into the Core Application Brain
        orchestrator = CheckoutOrchestrator(
            payment_port=payment_adapter, 
            queue_port=queue_adapter
        )

        print("--- SCENARIO: Unstable network, executing shielded adapter ---")
        
        # EXECUTION: The Orchestrator manages the logic flow (Sync -> Retry -> Fallback)
        status = orchestrator.process_checkout("ORD-LOCAL-99", 125.50)
        
        print(f"      [Final Result] Transaction State: {status.value}")

        # ARCHITECTURAL VERDICT
        print("\n" + "="*60)
        print("ARCHITECTURAL VERDICT: THE RESILIENT WAY WITH MESSAGE QUEUE FALLBACK")
        print("-" * 60)
        print("DURABILITY: Failure data is secured to disk (SQLite), not lost in RAM.")
        print("ZERO-TRUST: No external accounts or servers needed for the lab.")
        print("PURITY: The business logic is 100% library-agnostic.")
        print("\nREALITY CHECK: A Clarity Engineer ensures the Core survives.")
        print("="*60 + "\n")