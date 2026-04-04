from shared.log_manager import LogManager

class TwilioClient:
    """Mock of a 3rd party SMS library."""
    def __init__(self, key: str):
        self.key = key
        
    def send_sms(self, to: str, msg: str) -> None:
        # Referencing self.key ensures this remains an instance-dependent call
        LogManager.info("TwilioSDK", "Using Key: {0} to send to {1}: {2}", self.key, to, msg)

class FakeKafkaProducer:
    """Mock of a 3rd party messaging producer."""
    def produce(self, key: str, topic: str, value: str) -> None:
        LogManager.info("KafkaSDK", "Key: {0} | Topic: {1} | Data: {2}", key, topic, value)