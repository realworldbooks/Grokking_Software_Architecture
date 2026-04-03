from server_monitor import ServerMonitor
from attempted_test import AttemptedTest

class Demo:
    """
    The Execution Layer.
    This class acts as the 'Chief Explainer,' coordinating the 
    demonstration of the tightly coupled system.
    """
    
    @staticmethod
    def run() -> None:
        print("--- STARTING SCENARIO: TIGHT COUPLING (BEFORE) ---")
        
        # Step 1: Show the Happy Path / Real World usage
        # This demonstrates how the core logic is shackled to the infrastructure.
        monitor = ServerMonitor()
        
        print("Check 80 degrees: ", end="")
        monitor.check_temperature(80)
        
        print("Check 96 degrees: ", end="")
        monitor.check_temperature(96)

        print("\n----------------------------------------")

        # Step 2: Demonstrate the testing failure
        AttemptedTest.run()

        print("\n--- SCENARIO COMPLETE ---")

if __name__ == "__main__":
    Demo.run()