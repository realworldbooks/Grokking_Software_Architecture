from core.domain.server_monitor import ServerMonitor
from infrastructure.adapters.twilio_adapter import TwilioAdapter
from tests.server_monitor_test import ServerMonitorTests

class Demo:
    """
    The Execution Layer.
    This is where the Boundary Keeper defines the environment.
    It acts as the 'Chief Explainer' for the Hexagonal architecture.
    """

    @staticmethod
    def run() -> None:
        print("--- STARTING SERVER MONITOR (HEXAGONAL PYTHON) ---")

        # 1. Configuration (Injected from the environment)
        env_api_key = "SECRET_TWILIO_KEY_12345"
        env_phone_number = "555-999-8888"

        # 2. Adapter Selection (The 'Outside')
        twilio_adapter = TwilioAdapter(env_api_key, env_phone_number)

        # 3. Dependency Injection into the Core (The 'Inside')
        monitor = ServerMonitor(twilio_adapter)

        # 4. Execution
        print("Check 80 degrees: ", end="")
        monitor.check_temperature(80)  # Nominal case

        print("Check 105 degrees: ", end="")
        monitor.check_temperature(105) # Failure case triggers the adapter

        print("\n----------------------------------------\n")

        # 5. Automated Verification
        ServerMonitorTests.run()

        print("\n========================================")

if __name__ == "__main__":
    Demo.run()