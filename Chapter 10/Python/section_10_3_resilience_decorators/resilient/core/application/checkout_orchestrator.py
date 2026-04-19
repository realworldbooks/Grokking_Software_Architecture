import uuid
from ..domain.order_status import OrderStatus
from ..ports.payment_gateway import PaymentGateway
from ..ports.message_queue import MessageQueue

class CheckoutOrchestrator:
    """
    THE CORE APPLICATION LAYER:
    
    DESIGN NOTE:
    This is the "Brain" of the hexagon. It coordinates the business flow 
    using Ports. It doesn't know about 'tenacity' or 'requests'; it only 
    knows the 'Policy' for success and the 'Plan B' for failure.
    """
    def __init__(self, payment_port: PaymentGateway, queue_port: MessageQueue):
        self.payment_port = payment_port
        self.queue_port = queue_port

    def process_checkout(self, order_id: str, amount: float) -> OrderStatus:
        # Idempotency Key is a Business Concern
        idempotency_key = str(uuid.uuid4())
        
        try:
            # 1. THE HAPPY PATH
            self.payment_port.charge(amount, order_id, idempotency_key)
            print("      [Infrastructure] Transaction successful.")
            return OrderStatus.PAID
            
        except Exception:
            # THE FALLBACK (Plan B)
            # When the Adapter's retries fail, the Orchestrator pivots.
            print(f"      [FALLBACK] PRIMARY FAILED. Executing Plan B (Queueing).")
            
            self.queue_port.enqueue({
                "order_id": order_id,
                "amount": amount,
                "status": OrderStatus.PENDING_PAYMENT.value,
                "idempotency_key": idempotency_key
            })
            return OrderStatus.PENDING_PAYMENT