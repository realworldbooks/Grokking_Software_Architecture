# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/domain_models/order.py

from .customer import Customer
from .item import Item

class Order:
    """
    Represents a customer's order.

    This is a "Rich Domain Model". It encapsulates its own
    state (private fields and copies of lists) and handles
    its own business rules (e.g., price validation, calculating
    discounts). This solves the "Anemic Domain Model"
    anti-pattern where domain objects are just bags of getters
    and setters with no behavior.
    """
    GOLD_DISCOUNT_RATE = 0.9  # 10% discount

    def __init__(self, customer: Customer):
        self._customer = customer
        self._items = []
        self.total_price = 0.0

    def add_item(self, item: Item, quantity: int):
        if quantity <= 0:
            raise ValueError("Quantity must be positive.")
        
        # The Order class handles its own business logic.
        # Here, it calculates the price, applying a discount
        # if the customer is a gold member.
        price = item.price * quantity
        if self._customer.is_gold_member:
            price *= self.GOLD_DISCOUNT_RATE

        self._items.append({
            "item": item,
            "quantity": quantity,
            "price": price
        })
        self._update_total_price()

    def _update_total_price(self):
        self.total_price = sum(i['price'] for i in self._items)

    def get_items(self):
        # Return a copy to prevent external modification
        return list(self._items)

    def get_customer(self):
        # In a real system, you might return a copy or a
        # read-only view of the customer.
        return self._customer
