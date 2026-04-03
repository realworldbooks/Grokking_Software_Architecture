import json
from datetime import datetime
from core.ports.alert_port import AlertPort

class KafkaAdapter(AlertPort):
    """
    ADAPTER 3: The 'Scale' Adapter (Async Messaging).
    Shows how easy it is to swap a 'Sync' SMS for an 'Async' message.
    """

    def __init__(self, kafka_producer):
        self.kafka_producer = kafka_producer

    def send_alert(self, message: str) -> None:
        payload = json.dumps({
            "Error": message,
            "Timestamp": datetime.utcnow().isoformat()
        })
        
        # Fire and forget
        self.kafka_producer.produce("server-alerts-topic", payload)