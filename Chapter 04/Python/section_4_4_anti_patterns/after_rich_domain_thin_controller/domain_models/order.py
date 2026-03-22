# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/domain_models/order.py
import random
from .item import Item
from .customer import Customer

class Order:
    """
    THE RICH DOMAIN MODEL
    ARCHITECTURE NOTE: This solves the "Anemic Domain" anti-pattern.
    In the "Before" state, the Controller calculated the total and
    applied discounts. Now, the Order class is responsible for its 
    own data integrity.
    """
    GOLD_DISCOUNT_RATE = 0.9

    def __init__(self, customer_email: str):
        if not customer_email:
            raise ValueError("customer_email cannot be empty")
            
        self._customer_email = customer_email
        self._id = random.randint(1000, 9999) # Simulated ID
        self._total = 0.0
        
        # Encapsulation: Prevents external code from doing items.append() 
        # which would bypass our _recalculate_total logic.
        self._items = []

    # Encapsulation: External classes cannot arbitrarily change 
    # the total or the id. They must use these read-only properties.
    @property
    def id(self) -> int:
        return self._id

    @property
    def total(self) -> float:
        return self._total

    @property
    def customer_email(self) -> str:
        return self._customer_email

    @property
    def items(self) -> tuple:
        # Returning a tuple creates a read-only view of the list
        return tuple(self._items)

    def add_item(self, item: Item, customer: Customer):
        """
        Behavior is now co-located with the data it mutates.
        """
        # Business Rule: Prices must be positive
        if item.price <= 0:
            raise ValueError("Item price must be positive.")
            
        self._items.append(item)
        self._recalculate_total(customer)

    def _recalculate_total(self, customer: Customer):
        """
        The discount logic lives here! If another part of the system 
        creates an Order, they get this logic automatically. No more 
        duplicated logic scattered across controllers.
        """
        print("(DOMAIN) Calculating total...")
        sum_total = sum(i.price * i.quantity for i in self._items)
        
        if customer.type == "Gold":
            print("(DOMAIN) Applying Gold discount.")
            sum_total *= self.GOLD_DISCOUNT_RATE # 10% discount logic
            
        self._total = sum_total