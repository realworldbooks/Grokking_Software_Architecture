from core.domain import constants
from core.ports.alert_port import AlertPort

class ServerMonitor:
    """
    THE INSIDE (The Core).
    This is the Pure Domain Logic. It has been 'Isolated' from the 
    infrastructure. It contains zero references to Console, Twilio, or Kafka.
    """

    def __init__(self, alert_port: AlertPort):
        """
        Constructor Injection.
        We 'plug in' the adapter, allowing the Core to remain 
        agnostic of the specific implementation.
        """
        self.alert_port = alert_port

    def check_temperature(self, temp: int) -> None:
        """Evaluates temperature against domain constants."""
        if temp > constants.HIGH_TEMP_THRESHOLD:
            # The Core acts as the 'Boundary Keeper,' defining 'What' needs to 
            # happen, while leaving the 'How' to the outside world.
            self.alert_port.send_alert(f"Temp is {temp} degrees! Take cover!")
        else:
            print(f"[Core] Temp {temp} is normal.")