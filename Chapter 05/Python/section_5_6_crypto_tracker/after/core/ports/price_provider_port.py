from abc import ABC, abstractmethod

class PriceProviderPort(ABC):
    """
    PORT – Defines "What" we need (lives in Core).
    The ABC enforces the contract strictly.
    """
    
    @abstractmethod
    def get_bitcoin_price(self) -> float:
        pass