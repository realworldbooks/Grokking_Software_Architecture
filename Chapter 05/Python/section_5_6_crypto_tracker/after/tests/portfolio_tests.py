from core.domain.portfolio_manager import PortfolioManager
from infrastructure.adapters.fake_price_provider import FakePriceProvider

class PortfolioTests:
    """ARCHITECTURAL TEST"""

    @staticmethod
    def run() -> None:
        print("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---")
        
        # Arrange
        fake_adapter = FakePriceProvider(50000.0)
        manager = PortfolioManager(fake_adapter)

        # Act
        print("Test Action: Calculating value of 2 BTC at fixed $50,000 price...")
        value = manager.calculate_total_value(2.0)

        # Assert
        if value == 100000.0:
            print("SUCCESS: The portfolio correctly calculated $100,000. Test is stable!")
        else:
            print("FAIL: Math error in Core logic.")