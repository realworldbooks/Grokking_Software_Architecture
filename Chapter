"""
Listing 14.4 — Pattern B: The Enterprise Mocking Framework

Book file: com/ecommerce/order/tests/OrderMockitoMockTests.java
"""

import unittest
from unittest.mock import Mock

from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort
from section_14_6_instrumentation_logging.services.order_service import OrderService


class TestOrderMock(unittest.TestCase):
    """Pattern B: Dynamic mocking framework (unittest.mock)."""

    def test_order_checkout_with_mock(self) -> None:
        # 1. Arrange: Construct a highly instrumented dynamic proxy
        mock_payment_port: PaymentPort = Mock(spec=PaymentPort)
        mock_payment_port.process.return_value = True
        service = OrderService(mock_payment_port)

        # 2. Act: Trigger the system transaction path
        result = service.checkout("ord_99812", 150.00)

        # 3. Assert & Verify behavioral interaction contracts
        self.assertTrue(result)
        mock_payment_port.process.assert_called_once_with(150.00)


if __name__ == "__main__":
    unittest.main()