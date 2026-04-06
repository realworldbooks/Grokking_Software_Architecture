from .portfolio_manager import PortfolioManager
from shared.log_manager import LogManager

class AttemptedTest:
    """A demonstration of why Tight Coupling ruins testability."""
    
    @staticmethod
    def run() -> None:
        LogManager.info("AttemptedTest", "\n--- ATTEMPTING TO TEST (BEFORE) ---")
        
        manager = PortfolioManager()

        LogManager.info("AttemptedTest", "Test Action: Calculating value of 1 BTC...")
        
        try:
            value = manager.calculate_total_value(1.0)
            
            # ASSERT
            # We cannot assert equality because the live price is unpredictable.
            LogManager.info("AttemptedTest", "Result: {0}", value)
            LogManager.info("AttemptedTest", "FAIL: This test is FLAKY. We cannot assert a fixed price.")
        except Exception:
            LogManager.info("AttemptedTest", "CRASH: Test failed completely. No internet connection or API down.")