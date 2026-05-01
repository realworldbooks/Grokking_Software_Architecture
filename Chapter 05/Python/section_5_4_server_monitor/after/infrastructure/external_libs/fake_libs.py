class TwilioClient:
    """Mock of a 3rd party SMS library."""
    def __init__(self, key: str):
        self.key = key
        
    def send_sms(self, to: str, msg: str) -> None:
        # Referencing self.key ensures this remains an instance-dependent call
        print(f"[TwilioSDK] Using Key: {self.key} to send to {to}: {msg}")

class FakeKafkaProducer:
    """Mock of a 3rd party messaging producer."""
    def produce(self, key: str, topic: str, value: str) -> None:
        print(f"[KafkaSDK] Key: {key} | Topic: {topic} | Data: {value}")