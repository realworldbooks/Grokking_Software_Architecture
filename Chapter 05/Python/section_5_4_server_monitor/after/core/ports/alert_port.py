from abc import ABC, abstractmethod

class AlertPort(ABC):
    """
    PRIMARY PORT (Driven).
    Using Python's Abstract Base Classes to define the 'Contract'.
    Any adapter that inherits from this MUST implement the send_alert method,
    or Python will throw an instantiation error.
    """
    
    @abstractmethod
    def send_alert(self, message: str) -> None:
        """Sends an alert message to an external destination."""
        pass