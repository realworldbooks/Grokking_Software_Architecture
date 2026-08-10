"""
Demo runner for Section 14.6 — Unit Test and Instrumentation Logging.

Demonstrates Listings 14.1–14.5:
  14.1 PaymentPort (outbound infrastructure port)
  14.2 OrderService (ContextVar thread-local context)
  14.3 Pattern A: Hand-Coded Stub
  14.4 Pattern B: unittest.mock Mock
  14.5 Automated Telemetry Quality Gate
"""

import logging

from section_14_6_instrumentation_logging.observability.order_id_filter import OrderIdFormatter
from section_14_6_instrumentation_logging.ports.happy_path_payment_port import HappyPathPaymentPort
from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort
from section_14_6_instrumentation_logging.services.order_service import OrderService

# Configure logging so INFO-level telemetry messages are visible in the demo output.
# The OrderIdFormatter bridges the gap between Python's logging module and MDC:
# it reads the active ContextVar at format time and appends orderId to every
# log line, mirroring how Logback's %X{orderId} pattern works in Java.
logging.basicConfig(level=logging.INFO)
for handler in logging.getLogger().handlers:
    handler.setFormatter(OrderIdFormatter(
        fmt="%(asctime)s [%(levelname)s] %(message)s | orderId=%(orderId)s"
    ))


class Demo:
    """Runnable demo for Section 14.6."""

    @staticmethod
    def run() -> None:
        print("=== Section 14.6: Unit Test and Instrumentation Logging ===\n")

        # --- Listing 14.1: PaymentPort (abstract port defined in ports package) ---
        print("--- Listing 14.1: PaymentPort (Outbound Infrastructure Port) ---")
        print("PaymentPort defines the boundary contract:")
        print("    def process(self, amount: float) -> bool:")
        print("Core business logic depends on this abstraction, not a concrete HTTP client.\n")

        # --- Listing 14.2: OrderService with ContextVar (MDC equivalent) ---
        print("--- Listing 14.2: OrderService (ContextVar Thread-Local Context) ---")
        happy_path_port: PaymentPort = HappyPathPaymentPort()
        service = OrderService(happy_path_port)
        success = service.checkout("ord_99812", 150.00)
        print("Checkout result: " + str(success))
        print("ContextVar context after checkout (should be None): " + str(service.current_order_id))
        print()

        # --- Listing 14.3: Pattern A — Hand-Coded Stub ---
        print("--- Listing 14.3: Pattern A — Minimal Hand-Coded Stub ---")
        stub_service = OrderService(HappyPathPaymentPort())
        stub_result = stub_service.checkout("ord_99812", 150.00)
        print("Hand-coded stub checkout result: " + str(stub_result))
        print("(Stub is passive — cannot audit invocation counts.)\n")

        # --- Listing 14.4: Pattern B — unittest.mock (conceptual demo) ---
        print("--- Listing 14.4: Pattern B — Enterprise Mocking Framework (unittest.mock) ---")
        print("In the test suite, unittest.mock generates a dynamic proxy:")
        print("    mock = Mock(spec=PaymentPort)")
        print("    mock.process.return_value = True")
        print("    mock.process.assert_called_once_with(150.00)")
        print("The mock records invocation history and enforces interaction contracts.\n")

        # --- Listing 14.5: Automated Telemetry Quality Gate (conceptual demo) ---
        print("--- Listing 14.5: Automated Telemetry Quality Gate ---")
        print("The test suite intercepts the port boundary to assert ContextVar context:")
        print("    assert order_id_context.get() == 'ord_99812'")
        print("    assert order_id_context.get() is None  # after checkout")
        print("This guarantees telemetry compliance on every build.\n")

        print("=== Demo Complete ===")
        print("Run 'python -m unittest discover -s tests' to execute the full test suite.")
