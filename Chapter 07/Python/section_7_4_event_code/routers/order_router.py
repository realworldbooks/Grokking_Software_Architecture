# routers/order_router.py

import uuid
from ..core.event_publisher import EventPublisher
from ..shared.order_placed import OrderPlaced

class OrderRouter:
    """
    THE PRODUCER: The front-door entry point for the user.
    
    In Python frameworks like FastAPI, we use Routers to group related endpoints.
    Its only job is to translate user intent into an Event, throw it into the 
    queue, and return control to the user immediately.
    """
    def __init__(self, publisher: EventPublisher):
        # Dependency Inversion: We depend on the interface, not the concrete broker.
        self._publisher = publisher 

    async def checkout_async(self, user_id: uuid.UUID, amount: float) -> None:
        correlation_id = uuid.uuid4()
        order_id = uuid.uuid4()

        print(f"[Order API] POST /api/order/checkout received. CorrelationId: {correlation_id}")
        print(f"[Order API] Saving Order {order_id} to database...")

        # 1. Create the Immutable Fact
        order_placed = OrderPlaced(
            correlation_id=correlation_id,
            order_id=order_id,
            user_id=user_id,
            total_amount=amount
        )

        # 2. The Handoff: Push the event into the publisher.
        print(f"[Order API] Publishing OrderPlaced event to broker...")
        await self._publisher.publish_async(order_placed)

        # 3. Temporal Decoupling: We do not wait for shipping. We respond immediately.
        print(f"[Order API] HTTP 202 Accepted. User sees success instantly!")