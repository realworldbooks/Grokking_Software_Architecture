from .core.domain.portfolio_manager import PortfolioManager
from .infrastructure.adapters.coin_gecko_adapter import CoinGeckoAdapter
from .tests.portfolio_tests import PortfolioTests
from shared.log_manager import LogManager

class Demo:
    """The Execution Layer.
    
    Acts as the 'Boundary Keeper' that configures the environment 
    and triggers the domain logic.
    """

    @staticmethod
    def run() -> None:
        LogManager.info("Demo", "--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---")

        # 1. Dependency Injection (Plug in the real world)
        real_adapter = CoinGeckoAdapter()
        manager = PortfolioManager(real_adapter)

        # 2. Execution
        try:
            btc_to_check = 2.0
            value = manager.calculate_total_value(btc_to_check)
            
            # Professional currency formatting for the final summary
            print(f"Live Portfolio Value: ${value:,.2f}")
        except Exception as e:
            LogManager.info("Demo", "Live API failed, but architecture is safe: {0}", str(e))

        print("\n" + "-" * 40 + "\n")

        # 3. Verification
        PortfolioTests.run()
        
        LogManager.info("Demo", "========================================")
