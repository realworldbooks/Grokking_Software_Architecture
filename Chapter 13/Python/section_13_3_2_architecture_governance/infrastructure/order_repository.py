"""
The repository that persists Order entities to the database.

This is the Infrastructure layer's data access implementation.
It depends on the Domain layer (Order entity) - which is "below"
it in the dependency graph.

ARCHITECTURAL RULE: The Domain layer must NEVER reference this
module. Our fitness function enforces this boundary automatically.
"""

from typing import Optional

from ..domain.order import Order


class OrderRepository:
    """In-memory store simulating a real database repository."""

    def __init__(self) -> None:
        self._store: dict[str, Order] = {}

    def save(self, order: Order) -> Order:
        """Persists a new order to the (simulated) database."""
        self._store[order.id] = order
        return order

    def find_by_id(self, order_id: str) -> Optional[Order]:
        """Retrieves an order by its unique identifier."""
        return self._store.get(order_id)

    def find_by_customer(self, customer_name: str) -> list[Order]:
        """Retrieves all orders for a given customer."""
        return [o for o in self._store.values() if o.customer_name == customer_name]