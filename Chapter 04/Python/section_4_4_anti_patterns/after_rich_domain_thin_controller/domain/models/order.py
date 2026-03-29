import random
from typing import List, Tuple
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

    def __init__(self, customer):
        """
        Initializes a new instance of the Order class using a Rich Domain approach.
        
        ARCHITECTURE NOTE: By injecting the full Customer entity instead of just an email string, 
        the Order gains the "context" needed to calculate its own TotalPrice. 
        This eliminates the need for the Application Layer to manually handle business rules.
        """
        if not customer:
            raise ValueError("customer cannot be None")
        
        self._customer = customer
        self._id = random.randint(1000, 9999)
        self._items = []
        
        # Encapsulation: Prevents external code from doing items.append() 
        # which would bypass our _recalculate_total logic.
        self._items = []

    # Encapsulation: External classes cannot arbitrarily change 
    # the total or the id. They must use these read-only properties.
    @property
    def id(self) -> int:
        return self._id

    @property
    def is_eligible_for_discount(self):
        """Expression-bodied member for calculated data."""
        return self._customer is not None and self._customer.type == "Gold"

    @property
    def customer(self) -> str:
        return self._customer

    @property
    def items(self) -> tuple:
        # Returning a tuple creates a read-only view of the list
        return tuple(self._items)
    
    @property
    def customer_email(self):
        """
        ARCHITECTURE NOTE: We use an alias here to reflect the customer's current email.
        If the business required a 'snapshot' of the email at the time of purchase, 
        we would store this as a separate string field instead.
        """
        return self._customer.email
    
    @property
    def total_price(self):
        """
        The Atomic Truth: Logic and data are now perfectly unified.
        This property calculates the total based on current items and discount eligibility.
        """
        sum_total = sum(i.price * i.quantity for i in self._items)
        if self.is_eligible_for_discount:
            return sum_total * self.GOLD_DISCOUNT_RATE
        return sum_total

    def add_item(self, item: Item):
        """
        Behavior is now co-located with the data it mutates.
        """
        # Business Rule: Prices must be positive
        if item.price <= 0:
            raise ValueError("Item price must be positive.")     
        self._items.append(item)
        # No need to recalculate total here, as it's a property that calculates on demand.