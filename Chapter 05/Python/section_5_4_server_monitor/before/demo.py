from .server_monitor import ServerMonitor
from .attempted_test import AttemptedTest
from shared.log_manager import LogManager

class Demo:
    """
    The Execution Layer.
    This class acts as the 'Chief Explainer,' coordinating the 
    demonstration of the tightly coupled system.
    """
    
    @staticmethod
    def run() -> None:
        LogManager.info("Demo", "--- STARTING SCENARIO: TIGHT COUPLING (BEFORE) ---")
        
        # Step 1: Show the Happy Path / Real World usage
        # This demonstrates how the core logic is shackled to the infrastructure.
        monitor = ServerMonitor()
        
        LogManager.info("Demo", "Check 80 degrees: ")
        monitor.check_temperature(80)
        
        LogManager.info("Demo", "Check 96 degrees: ")
        monitor.check_temperature(96)

        LogManager.info("Demo", "\n----------------------------------------")

        # Step 2: Demonstrate the testing failure
        AttemptedTest.run()

        LogManager.info("Demo", "\n--- SCENARIO COMPLETE ---")