"""
The core Order entity. This is the heart of the domain model.

ARCHITECTURAL RULE: This class lives in the Domain layer and must
NEVER reference anything from the Infrastructure layer (no databases,
no HTTP clients, no external services). The Domain layer is the
protected core of the system - it contains pure business logic only.

Our fitness function (Listing 13.1) enforces this rule automatically
in the CI pipeline. If anyone adds a dependency from this class to
the Infrastructure layer, the build fails immediately.
"""

from dataclasses import dataclass, field
from datetime import datetime, timezone
from enum import Enum
from uuid import uuid4


class OrderStatus(Enum):
    """The lifecycle states of an Order.

    This enum lives in the Domain layer alongside the Order entity.
    It represents pure business vocabulary with no infrastructure
    dependencies whatsoever.
    """

    PENDING = "pending"
    PAID = "paid"
    SHIPPED = "shipped"
    CANCELLED = "cancelled"


@dataclass
class Order:
    """A customer order in the Shop-Zilla ecosystem.

    This is pure domain logic - no infrastructure involved.
    The Order class knows nothing about databases, HTTP clients,
    or any external services.
    """

    customer_name: str
    total_amount: float
    id: str = field(default_factory=lambda: str(uuid4()))
    status: OrderStatus = OrderStatus.PENDING
    created_at: datetime = field(default_factory=lambda: datetime.now(timezone.utc))

    def mark_as_paid(self) -> None:
        """Transitions the order to the PAID state.

        Raises:
            ValueError: If the order is not in PENDING state.
        """
        if self.status != OrderStatus.PENDING:
            raise ValueError("Only pending orders can be marked as paid.")
        self.status = OrderStatus.PAID

    def mark_as_shipped(self) -> None:
        """Transitions the order to the SHIPPED state.

        Raises:
            ValueError: If the order is not in PAID state.
        """
        if self.status != OrderStatus.PAID:
            raise ValueError("Only paid orders can be shipped.")
        self.status = OrderStatus.SHIPPED