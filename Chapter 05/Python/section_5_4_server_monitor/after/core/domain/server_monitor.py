from ...core.domain import constants
from ...core.ports.alert_port import AlertPort

class ServerMonitor:
    """THE INSIDE (The Core).
    
    This class represents the Pure Domain Logic of the system. It has been 
    'Isolated' from the infrastructure, meaning it contains zero references 
    to low-level details like Console, Twilio, or Kafka.
    """

    def __init__(self, alert_port: AlertPort):
        """Initializes the monitor with an abstract alert port.
        
        Args:
            alert_port (AlertPort): An implementation of the AlertPort contract
                (Constructor Injection). This allows the Core to remain 
                agnostic of the specific notification technology used.
        """
        self.alert_port = alert_port

    def check_temperature(self, temp: int) -> None:
        """Evaluates a temperature reading against business rules.
        
        The Core acts as the 'Boundary Keeper' here—it defines 'What' needs 
        to happen (an alert), while delegating the 'How' (the transport) 
        to the outside world.

        Args:
            temp (int): The current server temperature in degrees.
        """
        if temp > constants.HIGH_TEMP_THRESHOLD:
            # Domain logic decides that an alert is necessary
            self.alert_port.send_alert(f"Temp is {temp} degrees! Take cover!")
        else:
            print(f"[Core] Temp {temp} is normal.")