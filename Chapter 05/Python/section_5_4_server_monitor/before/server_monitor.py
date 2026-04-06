"""
The Core Business Logic.
WARNING: This class is a 'Liability' because it violates the 
Golden Rule of Separation of Concerns.
"""
from shared.log_manager import LogManager

class TwilioClient:
    """
    Mock of a 3rd party SMS library.
    In a real system, this is the 'Chaotic Outside World'.
    """
    def __init__(self, key: str):
        self.key = key

    def send_sms(self, to: str, body: str) -> None:
        LogManager.info("TwilioClient", "[Twilio API] Sending SMS to {0}: {1}", to, body)


class ServerMonitor:
    def check_temperature(self, temp: int) -> None:
        """Checks the server temperature and sends an alert if it's too high."""
        
        # VIOLATION: Hardcoded "magic number". 
        # This should be a configurable threshold.
        if temp > 95:
            # VIOLATION: Tight Coupling.
            # We are 'new-ing' up a concrete dependency inside our logic.
            # This makes the class impossible to unit test without a live API.
            twilio = TwilioClient("API_KEY")
            twilio.send_sms("555-1234", "Server is overheating!")
        else:
            LogManager.info("ServerMonitor", "Temp {0} is nominal.", temp)