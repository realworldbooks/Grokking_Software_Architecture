from abc import ABC, abstractmethod
from .order_request import OrderRequest

class IOrderService(ABC):
    """
    The Application Layer defines the contract for its own capabilities.
    """
    @abstractmethod
    def create_order(self, request: OrderRequest) -> OrderRequest:
        pass

    