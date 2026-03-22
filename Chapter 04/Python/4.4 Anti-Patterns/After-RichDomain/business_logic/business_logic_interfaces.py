# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/business_logic_interfaces.py
from abc import ABC, abstractmethod
from .order_request import OrderRequest

class IOrderService(ABC):
    """
    Defines the contract for the order service.
    The Presentation layer will depend on this interface.
    """
    @abstractmethod
    def create_order(self, request: OrderRequest):
        pass
