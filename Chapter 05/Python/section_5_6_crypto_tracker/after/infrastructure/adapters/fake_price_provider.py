from core.ports.price_provider_port import PriceProviderPort

class FakePriceProvider(PriceProviderPort):
    """
    ADAPTER 1: The 'Airplane Mode' / Test Adapter.
    """

    def __init__(self, fixed_price: float = 50000.0):
        self.fixed_price = fixed_price

    def get_bitcoin_price(self) -> float:
        return self.fixed_price