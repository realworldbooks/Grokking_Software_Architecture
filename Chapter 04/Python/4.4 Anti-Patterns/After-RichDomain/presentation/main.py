# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/presentation/main.py
# This is the "Composition Root" of the application.
# Its primary responsibility is to create and wire together the
# various components of the system.

from ..data_access.repositories import OrderRepository
from ..data_access.email_service import SmtpEmailService
from ..business_logic.order_service import OrderService
from .order_controller import OrderController

def main():
    print("App starting...")

    # 1. Create Data Access instances (concrete implementations)
    order_repo = OrderRepository()
    email_service = SmtpEmailService()

    # 2. Create Business Logic instance, injecting dependencies
    order_service = OrderService(order_repo, email_service)

    # 3. Create Presentation instance, injecting dependencies
    order_controller = OrderController(order_service)

    # --- Simulate an incoming web request ---
    # This would typically come from a web framework like Flask or Django
    simulated_request = {
        "customer_id": 123,
        "items": [
            {"item_id": 1, "quantity": 2},
            {"item_id": 2, "quantity": 1}
        ]
    }
    
    # 4. Trigger the application flow
    order_controller.create_order(simulated_request)
    
    print("App finished.")

if __name__ == "__main__":
    main()
