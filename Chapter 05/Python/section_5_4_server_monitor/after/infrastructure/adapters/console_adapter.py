from ...core.ports.alert_port import AlertPort
from shared.log_manager import LogManager

class ConsoleAdapter(AlertPort):
    """ADAPTER 2: The 'Dev' Adapter.
    
    This adapter provides immediate visual feedback during local development. 
    It proves that the Core remains identical regardless of whether the 
    alert goes to a cloud service or a terminal screen.
    """

    def send_alert(self, message: str) -> None:
        """Prints a high-visibility alert to the local console.
        
        Args:
            message (str): The alert message provided by the Core.
        """
        # ANSI escape codes for red text mimic professional UI feedback
        ansi_red = "\033[91m"
        ansi_reset = "\033[0m"
        
        # We wrap the output to ensure the 'Dev' context is clear, including ANSI codes for visual emphasis
        LogManager.info("ConsoleAdapter", f"{ansi_red}(DEV ADAPTER) ALERT: {{0}}{ansi_reset}", message)