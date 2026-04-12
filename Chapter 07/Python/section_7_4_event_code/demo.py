
import asyncio
import uuid
from .infrastructure.in_memory_broker import InMemoryBroker
from .routers.order_router import OrderRouter

class Demo:
    """
    THE COMPOSITION ROOT: Where we wire the decoupled pieces together.
    """
    @staticmethod
    async def run_async() -> None:
        print("=== Section 7.4: Event Definition & Decoupling ===\n")

        # 1. Stand up the Shared Infrastructure (The Event Bus)
        broker = InMemoryBroker()
        
        # 2. Start the continuous listening pipeline in the background.
        listener_task = asyncio.create_task(broker.start_listening_async())

        # 3. Inject the broker into the Router
        order_router = OrderRouter(broker)
        
        # 4. Simulate a user hitting the API to place an order.
        await order_router.checkout_async(uuid.uuid4(), 149.99)

        # 5. Pause the main thread to allow background workers to finish.
        await asyncio.sleep(2)
        
        # CLEANUP PHASE: Prevent Memory and Task Leaks
        listener_task.cancel() 
        
        print("=== Simulation Complete & Cleaned Up ===")
        print("Press Enter to return to menu...")