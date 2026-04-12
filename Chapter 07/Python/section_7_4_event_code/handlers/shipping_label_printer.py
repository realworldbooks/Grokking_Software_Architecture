# handlers/shipping_label_printer.py

import asyncio
from ..handlers.consumer import Consumer
from ..shared.order_placed import OrderPlaced

class ShippingLabelPrinter(Consumer[OrderPlaced]):
    """
    THE CONSUMER: A decoupled background worker.
    
    This simulates a completely independent system. Because of Temporal Decoupling,
    it operates entirely on its own timeline, processing work as fast as it can 
    without forcing the original web request to wait.
    """
    
    async def handle_async(self, event: OrderPlaced) -> None:
        print(f"\n[Shipping Service] Waking up...")
        
        # We extract the Correlation ID to link this background work 
        # back to the original user request in our centralized logs.
        print(f"[Shipping Service] CorrelationId matched: {event.correlation_id}")
        print(f"[Shipping Service] Printing Label for Order: {event.order_id}")
        print(f"[Shipping Service] Package Value: ${event.total_amount:.2f}")
        
        # asyncio.sleep() simulates network latency or physical work non-blockingly. 
        # While this thread sleeps, the Python event loop can process other messages.
        await asyncio.sleep(1) 
        
        # The Handshake: Telling the broker the job is successfully done
        print(f"[Shipping Service] Label Printed! ACK sent to broker.\n")