from ..infrastructure.repositories import SqlOrderRepository, SqlCustomerRepository, SqlItemRepository
from ..infrastructure.email_service import SmtpEmailService
from ..application.order_service import OrderService
from ..application.order_request import OrderRequest, OrderItemRequest

# ARCHITECTURE NOTE: We don't import the 'Item' Domain Model here. 
# The Presentation layer should only deal with DTOs, completely shielding 
# the internal Domain Models from the outside world.
from .controllers.order_controller import OrderController

def main():
    # --- THE COMPOSITION ROOT ---
    # ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
    # top of the 4-layer stack, it is responsible for wiring all the 
    # layers together via Dependency Injection.
    
    # 1. Instantiate the Infrastructure Layer
    order_repo = SqlOrderRepository()
    customer_repo = SqlCustomerRepository()
    item_repo = SqlItemRepository() # <-- Added the new secure lookup repository
    email_service = SmtpEmailService()

    # 2. Inject Data Access into the Application Layer
    order_service = OrderService(
        order_repo=order_repo,
        customer_repo=customer_repo,
        item_repo=item_repo, 
        email_service=email_service
    )

    # 3. Inject Business Logic into the Presentation Layer
    app = OrderController(order_service)

    print("--- Running Traditional 4-Layer Architecture ---")
    print("Fat Controller and Anemic Domain eliminated.")

    # --- Simulate an incoming HTTP request ---
    # ARCHITECTURE NOTE: Instead of passing a Domain Model with a vulnerable 
    # price that a user could manipulate, we pass a simple DTO containing 
    # only the item_id and the requested quantity.
    
    item_req1 = OrderItemRequest(item_id=1, quantity=1)
    item_req2 = OrderItemRequest(item_id=2, quantity=2)

    request = OrderRequest(
        customer_id=123,
        items=[item_req1, item_req2]
    )

    # Execute the controller endpoint
    response = app.create_order(request)
    print(f"HTTP 200 OK: {response}")

if __name__ == "__main__":
    main()