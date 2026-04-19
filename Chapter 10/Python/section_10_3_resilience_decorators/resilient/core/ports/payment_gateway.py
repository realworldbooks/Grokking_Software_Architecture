from abc import ABC, abstractmethod

class PaymentGateway(ABC):
    """
    THE CORE PORT (The Primary Airlock):
    
    DESIGN NOTE:
    This Port is the Core's definition of how it expects to interact with the 
    physical world. It belongs to the Core, not the Infrastructure.
    
    ARCHITECTURAL CRITIQUE:
    If this interface were in 'infrastructure', the Core would depend on 
    Infrastructure—violating the Golden Rule. By placing this in 'core/ports', 
    we force the external adapters to point INWARD to satisfy our business needs.
    """
    @abstractmethod
    def charge(self, amount: float, order_id: str, idempotency_key: str) -> bool:
        pass