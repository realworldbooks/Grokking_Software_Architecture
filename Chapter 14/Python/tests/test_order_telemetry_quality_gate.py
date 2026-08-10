"""
Listing 14.5 — Automated Telemetry Quality Gate

Book file: com/ecommerce/order/tests/OrderTelemetryQualityGateTests.java (inlined)
"""

import unittest

from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort
from section_14_6_instrumentation_logging.services.order_service import OrderService, order_id_context


class TestOrderTelemetryQualityGate(unittest.TestCase):
    """Verifies MDC/ContextVar context boundary compliance."""

    def test_checkout_should_maintain_context_boundary_during_execution(self) -> None:
        # Arrange: Intercept interface execution to read thread-local variables
        captured: dict[str, str | None] = {}

        class InterceptorPaymentPort(PaymentPort):
            def process(self, amount: float) -> bool:
                # Read active thread context values mid-transaction
                captured["order_id"] = order_id_context.get()
                return True

        service = OrderService(InterceptorPaymentPort())

        # Act: Trigger the system transaction path
        service.checkout("ord_99812", 75.00)

        # Assert: Context was present mid-transaction
        self.assertEqual(
            captured["order_id"], "ord_99812",
            "Telemetry Gap Error: ContextVar context was dropped before crossing the port boundary!"
        )

        # Assert: Ensure clean thread teardown to prevent memory context leaks
        self.assertIsNone(
            order_id_context.get(),
            "Memory Contamination Error: ContextVar context leaked past the request boundary lifetime!"
        )


if __name__ == "__main__":
    unittest.main()