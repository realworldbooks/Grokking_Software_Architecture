# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/domain_models/item.py

class Item:
    """
    Represents an item that can be added to an order.

    Like Customer, this is a simple data class. It holds
    information about a product but doesn't contain any
    business logic itself.
    """
    def __init__(self, id, name, price):
        if price <= 0:
            raise ValueError("Price must be positive.")
        self.id = id
        self.name = name
        self.price = price
