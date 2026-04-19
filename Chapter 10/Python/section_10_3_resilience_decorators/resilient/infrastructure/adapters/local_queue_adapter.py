import persistqueue
from ...core.ports.message_queue import MessageQueue

class LocalQueueAdapter(MessageQueue):
    """
    PHYSICAL INFRASTRUCTURE ADAPTER (Zero-Server Local Queue)
    
    @description 
    This adapter uses a disk-backed SQLite queue to simulate a decoupled 
    message broker. It provides durability without requiring external 
    accounts, API keys, or server installations.
    
    PRODUCTION ARCHITECTURE (How this works on a real server):
    In a live production environment (e.g., using RabbitMQ, AWS SQS, or Kafka), 
    this 'enqueue' method performs a 'Fire and Forget' handoff:
    
    1. THE PHYSICAL CONNECTION: Instead of writing to a local file, the 
       adapter maintains a TCP/IP connection (often persistent) to a 
       Message Broker cluster.
       
    2. SERIALIZATION & PROTOCOL: The Python dictionary is serialized 
       (typically to JSON or Protobuf) and wrapped in a protocol frame 
       (like AMQP or MQTT).
       
    3. THE HANDOFF (TEMPORAL DECOUPLING): The 'enqueue' call is non-blocking 
       from the Core's perspective. Once the Broker sends a 'Commit' or 'Ack' 
       confirming the message is safely in its own storage, the Application 
       is free to move on.
       
    4. WORKER INDEPENDENCE: A separate 'Consumer' or 'Worker' service 
       (potentially on a different server or auto-scaling cluster) polls 
       the Broker and eventually processes the payment. 
       
    *CRITICAL LESSON*: The Core doesn't care if the 'Worker' is alive or 
    dead at the moment of 'enqueue'. This is how we survive 
    infrastructure spikes and vendor downtime.
    """
    def __init__(self, path="./queue_storage"):
        # Creates a physical SQLite database on the local disk.
        # This acts as our "Message Broker" for the lab.
        self.queue = persistqueue.SQLiteQueue(path, auto_commit=True)

    def enqueue(self, payload: dict):
        """
        Physically persists the transaction data to the local disk.
        """
        # This is a synchronous write to the local database file.
        # It mimics the 'send' operation to a production message server.
        self.queue.put(payload)
        order_id = payload.get("order_id", "UNKNOWN")
        
        print(f"      [Local Queue] DATA PERSISTED: Order {order_id} secured in ./queue_storage/")