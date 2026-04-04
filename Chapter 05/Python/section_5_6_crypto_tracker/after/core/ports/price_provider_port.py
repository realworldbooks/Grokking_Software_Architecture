from abc import ABC, abstractmethod

class PriceProviderPort(ABC):
    """PRIMARY PORT (Driven).
    
    Defines 'What' the domain needs from the outside world.
    The Abstract Base Class enforces the contract strictly, ensuring 
    any infrastructure adapter is compatible with the Core.
    """
    
    @abstractmethod
    def get_bitcoin_price(self) -> float:
        """Retrieves the current market price of Bitcoin.
        
        Returns:
            float: The price of 1 BTC in USD.
        """
        pass