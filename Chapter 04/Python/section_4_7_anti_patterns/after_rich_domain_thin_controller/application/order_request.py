from typing import List

class OrderItemRequest:
    """
    DTO for individual items in the request. 
    Passes only the ID and quantity to prevent price tampering.
    """
    def __init__(self, item_id: int, quantity: int):
        self.item_id = item_id
        self.quantity = quantity

class OrderRequest:
    """
    DTO (Data Transfer Object) for incoming requests.
    ARCHITECTURE NOTE: We use a specific Request DTO rather than the 
    Domain Model to define our API contract. This prevents "Over-posting" 
    attacks where a user might try to send a fake price in the JSON.
    """
    def __init__(self, customer_id: int, items: List[OrderItemRequest]):
        self.customer_id = customer_id
        self.items = items