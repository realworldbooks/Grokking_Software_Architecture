# infrastructure/in_memory_broker.py

import asyncio
from ..core.event_publisher import EventPublisher
from ..shared.event import Event
from ..shared.order_placed import OrderPlaced
from ..handlers.shipping_label_printer import ShippingLabelPrinter

class InMemoryBroker(EventPublisher):
    """
    THE SHOCK ABSORBER: Simulates a real message broker using an asyncio.Queue.
    
    The queue safely holds messages in memory until a background consumer is 
    ready to pull them, protecting the system from sudden spikes in traffic.
    """
    def __init__(self):
        # asyncio.Queue is a thread-safe, async-native way to pass data 
        # between different tasks in the Python event loop.
        self.queue = asyncio.Queue()
        self.shipping_service = ShippingLabelPrinter()

    async def publish_async(self, event: Event) -> None:
        """
        Accepts a message and immediately returns control to the caller.
        """
        await self.queue.put(event)

    async def start_listening_async(self) -> None:
        """
        Simulates independent background workers pulling messages off the queue.
        This runs continuously in an infinite loop, completely separated from 
        the user-facing web API.
        """
        while True:
            try:
                # This will pause the loop until a message is placed in the queue
                event = await self.queue.get()
                
                # Routing logic: Ensure the right worker gets the right message
                if isinstance(event, OrderPlaced):
                    await self.shipping_service.handle_async(event)
                
                # Signal to the queue that the item has been fully processed
                self.queue.task_done()
                
            except asyncio.CancelledError:
                # This catches the cleanup signal from our Demo to shut down safely
                print("[Broker] Listener shutdown signal received. Memory safely reclaimed.")
                break