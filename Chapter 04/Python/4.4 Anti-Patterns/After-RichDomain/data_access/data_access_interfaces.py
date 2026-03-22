# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/data_access/data_access_interfaces.py
from abc import ABC, abstractmethod
# Note: In a traditional 4-layer architecture, the Data Access
# layer defines its own contracts (interfaces). Higher layers
# like Business Logic will depend on these abstractions.
# Python uses Abstract Base Classes (ABCs) to simulate interfaces.

class IOrderRepository(ABC):
    """
    Interface for saving an order. In a real app, this would
    handle database operations.
    """
    @abstractmethod
    def save_order(self, order):
        pass

class IEmailService(ABC):
    """
    Interface for sending emails. This isolates the business
    logic from the concrete email implementation (e.g., SMTP,
    SendGrid).
    """
    @abstractmethod
    def send_order_confirmation(self, order):
        pass
