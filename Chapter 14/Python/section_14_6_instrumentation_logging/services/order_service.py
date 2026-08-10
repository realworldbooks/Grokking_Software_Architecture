"""
Instrumented service layer handling transactional checkout workflows.
Embeds inside-out semantic telemetry using ContextVars (Python's equivalent
of Java's MDC / Thread-Local Storage).

Book listing: com.ecommerce.order.services.OrderService — Listing 14.2
"""

import logging
from contextvars import ContextVar
from section_14_6_instrumentation_logging.ports.payment_port import PaymentPort

# Python equivalent of Java's MDC (Mapped Diagnostic Context):
# ContextVar provides thread-local (and async-flowing) context sandboxing.
order_id_context: ContextVar[str | None] = ContextVar("order_id", default=None)

logger = logging.getLogger(__name__)


class OrderService:
    """Instrumented service layer with thread-local telemetry context."""

    def __init__(self, payment_port: PaymentPort) -> None:
        self._payment_port = payment_port

    @property
    def current_order_id(self) -> str | None:
        return order_id_context.get()

    def checkout(self, order_id: str, amount: float) -> bool:
        """Execute a checkout with MDC context binding."""
        token = order_id_context.set(order_id)
        try:
            logger.info("Executing transaction payment processing phase")

            payment_success = self._payment_port.process(amount)

            if not payment_success:
                logger.error("Payment transaction rejected by outbound payment port provider")
                return False

            logger.info("Transaction payment processed successfully")
            return True
        finally:
            # Restore previous context to prevent memory leaks / data contamination
            order_id_context.reset(token)