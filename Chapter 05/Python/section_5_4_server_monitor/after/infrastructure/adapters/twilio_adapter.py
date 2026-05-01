from ...core.ports.alert_port import AlertPort
from ...infrastructure.external_libs.fake_libs import TwilioClient

class TwilioAdapter(AlertPort):
    """THE ADAPTER (Production).
    
    Bridges the internal AlertPort to the external Twilio SMS API.
    This class is the 'Clarity Engineer' that translates domain 
    intent into infrastructure-specific SDK calls.
    """

    def __init__(self, api_key: str, target_phone_number: str):
        """Configures the adapter with environment-specific secrets.
        
        Args:
            api_key (str): The 'God Mode' secret used to auth with Twilio.
            target_phone_number (str): The recipient of the SMS alerts.
        """
        self.client = TwilioClient(api_key)
        self.target_phone_number = target_phone_number

    def send_alert(self, message: str) -> None:
        """Encapsulates the chaotic 3rd-party SDK call.
        
        Args:
            message (str): The message provided by the Core.
        """
        self.client.send_sms(self.target_phone_number, message)
        print(f"(PROD ADAPTER) SMS sent via Twilio: {message}")