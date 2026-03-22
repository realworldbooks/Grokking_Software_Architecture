from dataclasses import dataclass
from typing import Dict

@dataclass
class Option:
    """
    Represents a single architectural choice to be evaluated.
    
    This is a data class, a simple object whose main purpose is to hold data.
    The `@dataclass` decorator automatically generates special methods like
    `__init__`, making the code more concise.
    """
    name: str
    scores: Dict[str, int]  # e.g., {"performance": 5, "cost": 1}