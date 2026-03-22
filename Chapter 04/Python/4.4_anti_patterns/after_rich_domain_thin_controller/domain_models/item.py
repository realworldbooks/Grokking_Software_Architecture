# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/domain_models/item.py

class Item:
    """
    ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
    how items are priced and discounted is encapsulated inside the 
    Rich 'Order' model, not here.
    """
    def __init__(self):
        self.price = 0.0
        self.quantity = 0