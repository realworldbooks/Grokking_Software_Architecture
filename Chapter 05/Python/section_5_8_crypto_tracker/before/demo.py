from .portfolio_manager import PortfolioManager
from .attempted_test import AttemptedTest

class Demo:
    """The Execution Layer."""
    
    @staticmethod
    def run() -> None:
        print("--- STARTING SCENARIO: CRYPTO TRACKER (BEFORE) ---")
        
        manager = PortfolioManager()
        
        try:
            print("Calculating live value of 2 BTC...")
            value = manager.calculate_total_value(2.0)
            print(f"Portfolio Value: ${value}")
        except Exception as e:
            print(f"\nFailed. Do you have internet? {e}")

        print("\n----------------------------------------")

        AttemptedTest.run()
        
        print("\n========================================")
