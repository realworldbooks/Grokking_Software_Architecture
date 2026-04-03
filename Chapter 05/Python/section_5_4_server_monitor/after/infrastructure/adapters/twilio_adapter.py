from core.ports.alert_port import AlertPort
from infrastructure.external_libs.fake_libs import TwilioClient

class TwilioAdapter(AlertPort):
    """
    ADAPTER 1: The 'Real' Production Adapter.
    This class is the bridge between the internal AlertPort and the external Twilio API.
    """

    def __init__(self, api_key: str, target_phone_number: str):
        """Configuration is injected here, keeping 'God Mode' keys out of the Core."""
        self.api_key = api_key
        self.target_phone_number = target_phone_number

    def send_alert(self, message: str) -> None:
        # We encapsulate the 'Chaotic' 3rd party SDK here.
        client = TwilioClient(self.api_key)
        client.send_sms(self.target_phone_number, message)
        print(f"(PROD ADAPTER) SMS sent via Twilio: {message}")