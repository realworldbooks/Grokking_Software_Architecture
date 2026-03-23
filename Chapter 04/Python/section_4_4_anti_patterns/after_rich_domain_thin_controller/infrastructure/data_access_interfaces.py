from abc import ABC, abstractmethod
from ..domain.item import Item

class IOrderRepository(ABC):
    """
    ARCHITECTURE NOTE: In a traditional N-Tier architecture, the 
    Data Access Layer defines the contracts for accessing data. 
    The Business Logic layer above will be forced to depend on 
    this layer to use these interfaces.
    """
    @abstractmethod
    def get_by_id(self, order_id: int):
        pass

    @abstractmethod
    def save(self, order):
        pass

class IItemRepository(ABC):
    @abstractmethod
    def get_by_id(self, item_id: int) -> Item:
        pass

class ICustomerRepository(ABC):
    @abstractmethod
    def get_by_id(self, customer_id: int):
        pass

class IEmailService(ABC):
    @abstractmethod
    def send(self, to: str, subject: str, body: str):
        pass