import json
from datetime import datetime
from ...core.ports.alert_port import AlertPort

class KafkaAdapter(AlertPort):
    """ADAPTER 3: The 'Scale' Adapter (Async Messaging).
    
    This implementation of the AlertPort allows the system to scale by 
    offloading alerts to a message broker. It demonstrates how easily 
    a synchronous action (SMS) can be swapped for an asynchronous one.
    """

    def __init__(self, kafka_producer):
        """Initializes the adapter with a messaging client.
        
        Args:
            kafka_producer (FakeKafkaProducer): The 3rd-party producer 
                responsible for pushing data to the broker.
        """
        self.kafka_producer = kafka_producer

    def send_alert(self, message: str) -> None:
        """Formats the alert as JSON and dispatches it with a routing key.
        
        Args:
            message (str): The raw alert string from the Core.
        """
        payload = json.dumps({
            "Error": message,
            "Timestamp": datetime.utcnow().isoformat()
        })
        
        # We use a static key to ensure partition affinity (ordering)
        # for this specific server in the Kafka cluster.
        self.kafka_producer.produce("Server-01", "server-alerts-topic", payload)
        print("(SCALE ADAPTER) Pushed to Kafka topic")