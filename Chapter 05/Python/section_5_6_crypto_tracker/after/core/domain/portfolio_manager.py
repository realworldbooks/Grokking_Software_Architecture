from core.ports.price_provider_port import PriceProviderPort

class PortfolioManager:
    """
    CORE – Pure business logic.
    No HTTP clients, no JSON parsing. This class is fully isolated.
    """

    def __init__(self, price_provider: PriceProviderPort):
        """Dependency Injection via Constructor."""
        self.price_provider = price_provider

    def calculate_total_value(self, btc_amount: float) -> float:
        # We just call the port. We don't care WHERE the price comes from.
        current_price = self.price_provider.get_bitcoin_price()
        return btc_amount * current_price