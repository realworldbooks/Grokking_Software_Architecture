from dataclasses import dataclass
from typing import List

"""
A Simple Data Transfer Object (DTO).
* ARCHITECTURE NOTE: This class is a pure data container. Its only 
responsibility is to hold the state of an order as it moves through 
the system. 
* By using a @dataclass, we create a clear "contract" that the 
OrderService and its components (Validator, Payment, etc.) can rely on. 
The problem in our "Before" state wasn't this data—it was the 
monolithic way we processed it.
"""
@dataclass
class Order:
    """
    Represents the stable data structure for an order.
    """
    items: List[str]
    total: float
    customer_email: str