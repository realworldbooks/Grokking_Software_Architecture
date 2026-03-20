from order import Order
from services import OrderValidator, PaymentService, InventoryManager, NotificationService
from order_service import OrderService

if __name__ == "__main__":
    print("=== Chapter 3: Order Processor (AFTER) ===")
    print("A coordinator class delegates to focused services...\n")

    order = Order(items=["Book", "Pen"], total=25.50, customer_email="customer@example.com")
    
    service = OrderService(
        OrderValidator(),
        PaymentService(),
        InventoryManager(),
        NotificationService()
    )

    result = service.process_order(order)

    print(f"\nRESULT: {result}")
    print("==========================================\n")