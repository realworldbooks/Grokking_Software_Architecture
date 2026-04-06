from ..core.domain.server_monitor import ServerMonitor
from ..tests.fake_alert_port import FakeAlertPort
from shared.log_manager import LogManager

class ServerMonitorTests:
    """
    ARCHITECTURAL TEST
    Fulfills the Scribe role by proving the test passes without hitting a real API.
    """

    @staticmethod
    def run() -> None:
        LogManager.info("ServerMonitorTests", "--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---")
        
        # Arrange
        fake_port = FakeAlertPort()
        monitor = ServerMonitor(fake_port)

        # Act
        LogManager.info("ServerMonitorTests", "Test Action: Checking temperature at 96 degrees...")
        monitor.check_temperature(96)

        # Assert
        if len(fake_port.sent_messages) == 1 and "Take cover" in fake_port.sent_messages[0]:
            LogManager.info("ServerMonitorTests", "SUCCESS: Alert sent correctly to the Port.")
        else:
            LogManager.info("ServerMonitorTests", "FAIL: Alert logic failed verification.")