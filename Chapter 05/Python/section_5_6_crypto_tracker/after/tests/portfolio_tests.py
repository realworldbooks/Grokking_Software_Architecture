import math
from ..core.domain.portfolio_manager import PortfolioManager
from ..infrastructure.adapters.fake_price_provider import FakePriceProvider

class PortfolioTests:
    """ARCHITECTURAL TEST.
    
    Fulfills the 'Scribe' role by using a deterministic Fake to verify 
    business logic without precision-related flakiness.
    """

    @staticmethod
    def run() -> None:
        print("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---")
        
        # Arrange
        fake_adapter = FakePriceProvider(50000.0)
        manager = PortfolioManager(fake_adapter)

        # Act
        print("Test Action: Calculating value of 2 BTC...")
        value = manager.calculate_total_value(2.0)

        # Assert: Use math.isclose to avoid floating-point equality traps
        expected_value = 100000.0
        
        if math.isclose(value, expected_value, rel_tol=1e-9):
            print("SUCCESS: The portfolio correctly calculated $100,000. Test is stable!")
        else:
            print(f"FAIL: Math error or precision issue. Expected {expected_value}, but got {value}")