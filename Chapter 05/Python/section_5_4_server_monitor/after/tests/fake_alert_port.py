from core.ports.alert_port import AlertPort

class FakeAlertPort(AlertPort):
    """
    Fake port for isolated testing.
    Extracted into its own file so it can be reused across multiple test suites.
    """

    def __init__(self):
        self.sent_messages = []

    def send_alert(self, message: str) -> None:
        self.sent_messages.append(message)