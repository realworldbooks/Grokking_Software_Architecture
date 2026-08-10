"""
The HTTP API controller that exposes Order operations to clients.

This is the Presentation layer - the outermost ring of our
architecture. It depends on the Application/Domain layers below it.

ARCHITECTURAL RULE: This class must:
  1. Inherit from BaseController (enforced by fitness function)
  2. End with the "Controller" suffix (enforced by fitness function)
  3. Reside in the Presentation package (enforced by fitness function)

If any of these rules are violated, the CI pipeline fails the build.
"""

from typing import Optional

from ..domain.order import Order
from ..infrastructure.order_repository import OrderRepository
from .base_controller import BaseController


class OrderController(BaseController):
    """HTTP API controller exposing Order operations to clients."""

    def __init__(self, repository: OrderRepository) -> None:
        self._repository = repository

    def get_by_id(self, order_id: str) -> Optional[Order]:
        """GET /api/order/{id} - Retrieves a single order by ID."""
        return self._repository.find_by_id(order_id)

    def create(self, customer_name: str, total_amount: float) -> Order:
        """POST /api/order - Creates a new order."""
        order = Order(customer_name=customer_name, total_amount=total_amount)
        return self._repository.save(order)

    def get_by_customer(self, customer_name: str) -> list[Order]:
        """GET /api/order/customer/{name} - Retrieves all orders for a customer."""
        return self._repository.find_by_customer(customer_name)