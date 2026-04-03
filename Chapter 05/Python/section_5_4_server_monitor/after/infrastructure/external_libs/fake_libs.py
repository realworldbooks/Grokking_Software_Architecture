"""
Dummies to allow execution without installing real pip packages.
This represents the 'Chaotic Outside World'.
"""

class TwilioClient:
    def __init__(self, key: str):
        self.key = key
        
    def send_sms(self, to: str, msg: str) -> None:
        # Simulation of a network call
        pass

class FakeKafkaProducer:
    def produce(self, topic: str, value: str) -> None:
        print(f"[Kafka] Pushed to {topic}: {value}")