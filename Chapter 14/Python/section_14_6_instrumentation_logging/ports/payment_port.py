"""
Outbound port definition for third-party billing interactions.
Decouples core processing execution from concrete network clients.

Book listing: com.ecommerce.order.ports.PaymentPort — Listing 14.1
"""

from abc import ABC, abstractmethod


class PaymentPort(ABC):
    """Abstract base class defining the payment processing boundary contract."""

    @abstractmethod
    def process(self, amount: float) -> bool:
        """Process a payment for the given amount."""
        ...