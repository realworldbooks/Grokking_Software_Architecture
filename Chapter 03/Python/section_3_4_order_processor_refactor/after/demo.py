from .order import Order
from .order_service_components import OrderValidator, PaymentService, InventoryManager, NotificationService
from .order_service import OrderService

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: Order Processor (AFTER) ===")
        print("A coordinator class delegates to focused services...\n")
        
        # Create the data
        order = Order(items=["Book", "Pen"], total=25.50, customer_email="customer@example.com")
        
        # Wire up the system (Dependency Injection)
        # This is the ONLY place where we 'glue' these specific classes together.
        service = OrderService(
            OrderValidator(),
            PaymentService(),
            InventoryManager(),
            NotificationService()
        )

        result = service.process_order(order)

        print(f"\nRESULT: {result}")
        print("==========================================\n")
