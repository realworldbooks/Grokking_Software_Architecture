from abc import ABC, abstractmethod
from ..domain.order_status import OrderStatus

class MessageQueue(ABC):
    """
    THE CORE PORT (The Asynchronous Interface)
    
    @description
    This abstract base class defines the system's ability to defer work. 
    It is a "Primary Boundary" that allows the Business Logic to offload 
    transactions when synchronous gateways (like FlakyPayments) are unreachable.
    
    PRODUCTION REALITY (How this works on a Server):
    While our local adapter uses SQLite, this Port represents the 
    standard 'Producer' pattern found in distributed systems:
    
    1. INDEPENDENCE: The Business Logic calls 'enqueue' and immediately 
       receives a return. It does not wait for the payment to actually 
       be processed.
       
    2. RELIABILITY: In production (RabbitMQ/SQS), this Port ensures that 
       once a message is accepted by the broker, it is stored on multiple 
       disks/nodes so it cannot be lost if a single server crashes.
       
    3. LOAD LEVELING: This Port acts as a 'Buffer.' If 10,000 orders 
       arrive at once, they sit safely in the Queue Port until the 
       Background Workers can process them at a steady pace.
    """

    @abstractmethod
    def enqueue(self, order_id: str, amount: float, status: OrderStatus, key: str):
        """
        Defines the requirement for persisting an order for later processing.
        
        :param order_id: The unique business identifier for the order.
        :param amount: The financial value of the transaction.
        :param status: The current state (e.g., PENDING_PAYMENT).
        :param key: The Idempotency Key to prevent double-charging during recovery.
        """
        pass