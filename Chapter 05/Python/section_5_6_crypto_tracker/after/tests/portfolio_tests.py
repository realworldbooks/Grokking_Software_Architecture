import math
from ..core.domain.portfolio_manager import PortfolioManager
from ..infrastructure.adapters.fake_price_provider import FakePriceProvider
from shared.log_manager import LogManager

class PortfolioTests:
    """ARCHITECTURAL TEST.
    
    Fulfills the 'Scribe' role by using a deterministic Fake to verify 
    business logic without precision-related flakiness.
    """

    @staticmethod
    def run() -> None:
        """Executes the hexagonal test suite for the Crypto Tracker."""
        LogManager.info("PortfolioTests", "--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---")
        
        # Arrange
        fake_adapter = FakePriceProvider(50000.0)
        manager = PortfolioManager(fake_adapter)

        # Act
        LogManager.info("PortfolioTests", "Test Action: Calculating value of 2 BTC...")
        value = manager.calculate_total_value(2.0)

        # Assert: Use math.isclose to avoid floating-point equality traps
        expected_value = 100000.0
        
        if math.isclose(value, expected_value, rel_tol=1e-9):
            LogManager.info(
                "PortfolioTests", 
                "SUCCESS: The portfolio correctly calculated $100,000. Test is stable!"
            )
        else:
            LogManager.info(
                "PortfolioTests", 
                "FAIL: Math error or precision issue. Expected {0}, but got {1}", 
                expected_value, 
                value
            )