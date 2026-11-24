from abc import ABC, abstractmethod
from .order import Order

class OrderRepository(ABC):
    """
    THE ABSTRACTION (INTERFACE).
    ARCHITECTURE NOTE: In Python, we use ABC to define a 
    formal contract. This is the 'Inversion' point. 
    The Service depends on this abstract role, not 
    a concrete database implementation.
    """
    @abstractmethod
    def save(self, order: Order):
        """
        Contract: Any subclass must implement this method.
        """
        pass