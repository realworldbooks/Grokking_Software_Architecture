from .portfolio_manager import PortfolioManager
from .attempted_test import AttemptedTest
from shared.log_manager import LogManager

class Demo:
    """The Execution Layer."""
    
    @staticmethod
    def run() -> None:
        LogManager.info("Demo", "--- STARTING SCENARIO: CRYPTO TRACKER (BEFORE) ---")
        
        manager = PortfolioManager()
        
        try:
            LogManager.info("Demo", "Calculating live value of 2 BTC...")
            value = manager.calculate_total_value(2.0)
            LogManager.info("Demo", "Portfolio Value: ${0}", value)
        except Exception as e:
            LogManager.info("Demo", "\nFailed. Do you have internet? {0}", e)

        LogManager.info("Demo", "\n----------------------------------------")

        AttemptedTest.run()
        
        LogManager.info("Demo", "\n========================================")
