# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/order_request.py
from typing import List
from ..domain_models.item import Item

class OrderRequest:
    """
    DTO (Data Transfer Object) for incoming requests.
    """
    def __init__(self, customer_id: int, items: List[Item]):
        self.customer_id = customer_id
        self.items = items