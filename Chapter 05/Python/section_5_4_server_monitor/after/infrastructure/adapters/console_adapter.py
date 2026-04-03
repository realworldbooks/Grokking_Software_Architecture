from core.ports.alert_port import AlertPort

class ConsoleAdapter(AlertPort):
    """
    ADAPTER 2: The 'Dev' Adapter.
    Proves to Archie that the Core doesn't care if the alert goes to a 
    cloud messaging service or simply prints to the local screen.
    """

    def send_alert(self, message: str) -> None:
        # We use ANSI escape codes to mimic a real red alert,
        # but the Core logic remains completely unaware of this UI detail.
        ansi_red = "\033[91m"
        ansi_reset = "\033[0m"
        print(f"{ansi_red}(DEV ADAPTER) ALERT: {message}{ansi_reset}")