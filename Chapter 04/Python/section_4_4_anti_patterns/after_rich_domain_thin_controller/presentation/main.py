# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/presentation/main.py

from ..data_access.repositories import SqlOrderRepository, SqlCustomerRepository
from ..data_access.email_service import SmtpEmailService
from ..business_logic.order_service import OrderService
from ..business_logic.order_request import OrderRequest
from ..domain_models.item import Item
from .controllers.order_controller import OrderController

def main():
    # --- THE COMPOSITION ROOT ---
    # ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
    # top of the 4-layer stack, it is responsible for wiring all the 
    # layers together via Dependency Injection.
    
    # 1. Instantiate the Data Access Layer (Infrastructure)
    order_repo = SqlOrderRepository()
    customer_repo = SqlCustomerRepository()
    email_service = SmtpEmailService()

    # 2. Inject Data Access into the Business Logic Layer
    order_service = OrderService(
        order_repo=order_repo,
        customer_repo=customer_repo,
        email_service=email_service
    )

    # 3. Inject Business Logic into the Presentation Layer
    app = OrderController(order_service)

    print("--- Running Traditional 4-Layer Architecture ---")
    print("Fat Controller and Anemic Domain eliminated.")

    # --- Simulate an incoming HTTP request ---
    item1 = Item()
    item1.price = 100.0
    item1.quantity = 1

    item2 = Item()
    item2.price = 50.0
    item2.quantity = 2

    request = OrderRequest(
        customer_id=123,
        items=[item1, item2]
    )

    # Execute the controller endpoint
    response = app.create_order(request)
    print(f"HTTP 200 OK: {response}")

if __name__ == "__main__":
    main()