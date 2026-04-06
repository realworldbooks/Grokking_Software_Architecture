from .core.domain.server_monitor import ServerMonitor
from .infrastructure.adapters.twilio_adapter import TwilioAdapter
from .tests.server_monitor_test import ServerMonitorTests
from shared.log_manager import LogManager

class Demo:
    """
    The Execution Layer.
    This is where the Boundary Keeper defines the environment.
    It acts as the 'Chief Explainer' for the Hexagonal architecture.
    """

    @staticmethod
    def run() -> None:
        LogManager.info("Demo", "--- STARTING SERVER MONITOR (HEXAGONAL PYTHON) ---")

        # 1. Configuration (Injected from the environment)
        env_api_key = "SECRET_TWILIO_KEY_12345"
        env_phone_number = "555-999-8888"

        # 2. Adapter Selection (The 'Outside')
        # ARCHITECTURE NOTE: This is the "Composition Root" for this example.
        # We are wiring up the concrete implementation (TwilioAdapter) to the abstraction.
        twilio_adapter = TwilioAdapter(env_api_key, env_phone_number)

        # 3. Dependency Injection into the Core (The 'Inside')
        monitor = ServerMonitor(twilio_adapter)

        # 4. Execution
        LogManager.info("Demo", "Check 80 degrees: ")
        monitor.check_temperature(80)  # Nominal case

        LogManager.info("Demo", "Check 105 degrees: ")
        monitor.check_temperature(105) # Failure case triggers the adapter

        LogManager.info("Demo", "") # Add a blank line for spacing
        LogManager.info("Demo", "----------------------------------------")
        LogManager.info("Demo", "") # Add a blank line for spacing

        # 5. Automated Verification
        ServerMonitorTests.run()

        LogManager.info("Demo", "") # Add a blank line for spacing
        LogManager.info("Demo", "========================================")
