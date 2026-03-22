# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/i_order_service.py
from abc import ABC, abstractmethod
from .order_request import OrderRequest

class IOrderService(ABC):
    """
    The Business Layer defines the contract for its own capabilities.
    """
    @abstractmethod
    def create_order(self, request: OrderRequest) -> int:
        pass