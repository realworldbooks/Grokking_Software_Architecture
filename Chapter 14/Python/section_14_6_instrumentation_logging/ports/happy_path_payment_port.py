"""
A concrete, hand-coded stub implementation of PaymentPort.
Simulates a successful payment with zero network overhead.
"""

from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort


class HappyPathPaymentPort(PaymentPort):
    """Hand-coded stub that always returns success."""

    def process(self, amount: float) -> bool:
        return True