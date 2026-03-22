# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/order_request.py

class OrderRequest:
    """
    A Data Transfer Object (DTO) used to pass data from the
    Presentation layer to the Business Logic layer.
    
    This is not a domain model. It's a simple, flat data
    structure that carries information for creating an order.
    """
    def __init__(self, customer_id, items):
        # items is expected to be a list of dicts,
        # e.g., [{"item_id": 1, "quantity": 2}]
        self.customer_id = customer_id
        self.items = items
