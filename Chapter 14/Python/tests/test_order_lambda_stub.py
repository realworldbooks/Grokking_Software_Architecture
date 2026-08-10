"""
Listing 14.3 — Pattern A: The Minimal Hand-Coded Stub

Book file: com/ecommerce/order/tests/OrderLambdaStubTests.java

NOTE: The book's Java example uses an inline lambda (Java interfaces with a
single method are functional). Python ABCs are NOT directly lambda-callable,
so we use an equivalent hand-coded concrete stub class (HappyPathPaymentPort)
that encapsulates the exact same "always return true" behavior.
"""

import unittest

from section_14_6_instrumentation_logging.ports.happy_path_payment_port import HappyPathPaymentPort
from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort
from section_14_6_instrumentation_logging.services.order_service import OrderService


class TestOrderLambdaStub(unittest.TestCase):
    """Pattern A: Minimal hand-coded stub."""

    def test_order_checkout_with_inline_stub(self) -> None:
        # Create a sterile, static Test Double with zero network overhead
        inline_payment_stub: PaymentPort = HappyPathPaymentPort()
        service = OrderService(inline_payment_stub)

        result = service.checkout("ord_99812", 150.00)
        self.assertTrue(result, "Checkout failed under a cooperative happy-path stub.")


if __name__ == "__main__":
    unittest.main()