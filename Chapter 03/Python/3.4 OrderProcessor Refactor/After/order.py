from dataclasses import dataclass
from typing import List

@dataclass
class Order:
    items: List[str]
    total: float
    customer_email: str