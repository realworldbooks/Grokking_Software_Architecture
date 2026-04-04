from ...core.ports.price_provider_port import PriceProviderPort
from shared.log_manager import LogManager

class PortfolioManager:
    """THE INSIDE (The Core).
    
    Contains the pure business logic for financial calculations.
    This class is fully isolated from HTTP clients and JSON parsing, 
    relying entirely on an abstract PriceProviderPort.
    """

    def __init__(self, price_provider: PriceProviderPort):
        """Initializes the manager with a price provider.
        
        Args:
            price_provider (PriceProviderPort): An adapter that satisfies 
                the get_bitcoin_price contract (Constructor Injection).
        """
        self.price_provider = price_provider

    def calculate_total_value(self, btc_amount: float) -> float:
        """Calculates the total market value of a Bitcoin holding.
        
        Args:
            btc_amount (float): The quantity of Bitcoin to evaluate.
            
        Returns:
            float: The total value in USD.
        """
        # We call the port. We don't care WHERE the price comes from.
        current_price = self.price_provider.get_bitcoin_price()
        total_value = btc_amount * current_price
        
        # Professional trace logging for developers to see the math breakdown
        LogManager.info(
            "PortfolioManager", 
            f"[Core] Calculating: {btc_amount} BTC x ${current_price:,.2f}/BTC"
        )
        
        return total_value