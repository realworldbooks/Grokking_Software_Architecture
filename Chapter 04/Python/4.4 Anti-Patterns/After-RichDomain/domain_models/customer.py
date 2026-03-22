# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/domain_models/customer.py

class Customer:
    """
    Represents a customer.

    This is a simple data-carrying class with no logic,
    representing an entity in our domain. In a real system,
    this might have more complexity, but for this example, it's
    just a container for customer information.
    """
    def __init__(self, id, name, is_gold_member=False):
        self.id = id
        self.name = name
        self.is_gold_member = is_gold_member
