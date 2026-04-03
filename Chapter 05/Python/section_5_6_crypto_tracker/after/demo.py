from core.domain.portfolio_manager import PortfolioManager
from infrastructure.adapters.coin_gecko_adapter import CoinGeckoAdapter
from tests.portfolio_tests import PortfolioTests

class Demo:
    """The Execution Layer."""

    @staticmethod
    def run() -> None:
        print("--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---")

        real_adapter = CoinGeckoAdapter()
        manager = PortfolioManager(real_adapter)

        try:
            value = manager.calculate_total_value(2.0)
            print(f"Live Portfolio Value: ${value}")
        except Exception as e:
            print(f"Live API failed, but architecture is safe: {e}")

        print("\n----------------------------------------\n")

        PortfolioTests.run()
        
        print("\n========================================")

if __name__ == "__main__":
    Demo.run()