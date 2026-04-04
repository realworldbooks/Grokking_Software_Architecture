from .portfolio_manager import PortfolioManager

class AttemptedTest:
    """A demonstration of why Tight Coupling ruins testability."""
    
    @staticmethod
    def run() -> None:
        print("\n--- ATTEMPTING TO TEST (BEFORE) ---")
        
        manager = PortfolioManager()

        print("Test Action: Calculating value of 1 BTC...")
        
        try:
            value = manager.calculate_total_value(1.0)
            
            # ASSERT
            # We cannot assert equality because the live price is unpredictable.
            print(f"Result: {value}")
            print("FAIL: This test is FLAKY. We cannot assert a fixed price.")
        except Exception:
            print("CRASH: Test failed completely. No internet connection or API down.")