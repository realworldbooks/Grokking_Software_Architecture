"""
Python equivalent of Java's Mapped Diagnostic Context (MDC) enrichment.

The `logging` module in Python does not automatically inject context
variables into log records (unlike Logback's %X{orderId} pattern).
This custom Formatter bridges that gap: it reads the active ContextVar
at format time and appends the orderId to every log line, mirroring
how Logback's %X{orderId} pattern works in Java.
"""

import logging

from section_14_6_instrumentation_logging.services.order_service import order_id_context


class OrderIdFormatter(logging.Formatter):
    """
    Logging formatter that appends the active orderId context to each log line.

    Usage:
        handler.setFormatter(OrderIdFormatter(
            fmt="%(asctime)s [%(levelname)s] %(message)s | orderId=%(orderId)s"
        ))
    """

    def format(self, record: logging.LogRecord) -> str:
        # Inject the active ContextVar value into the record before formatting.
        # This is the Python equivalent of Logback's %X{orderId} MDC pattern.
        record.orderId = order_id_context.get() or "none"
        return super().format(record)