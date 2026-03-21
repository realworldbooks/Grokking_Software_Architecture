from dataclasses import dataclass
from typing import List

"""
A Simple Data Transfer Object (DTO).
* ARCHITECTURE NOTE: This is a perfect use of a Python dataclass. 
It is a passive data container. The problem we will solve in the refactor 
isn't this data, but the way the Processor class "reaches into" it 
to perform disparate business rules.
"""
@dataclass
class Order:
    items: List[str]
    total: float
    customer_email: str