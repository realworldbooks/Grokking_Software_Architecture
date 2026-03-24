from .i_order_service import IOrderService
from .order_request import OrderRequest
from .order_response import OrderResponse
from ..domain.models.order import Order

# THE DOWNWARD DEPENDENCY
from ..domain.interfaces.data_access_interfaces import (
    IOrderRepository,
    ICustomerRepository,
    IItemRepository, 
    IEmailService
)

class OrderService(IOrderService):
    """
    THE SERVICE LAYER (Orchestrator)
    ARCHITECTURE NOTE: This class replaces the massive "God Method" 
    from the Fat Controller. It doesn't write to the DB, nor does 
    it calculate math. It simply coordinates the flow of data 
    between the Data Access layer and the Rich Domain Models.
    """
    def __init__(
        self,
        order_repo: IOrderRepository,
        customer_repo: ICustomerRepository,
        item_repo: IItemRepository, # New dependency
        email_service: IEmailService
    ):
        # Dependencies on the Data Access layer below it
        self._order_repo = order_repo
        self._customer_repo = customer_repo
        self._item_repo = item_repo
        self._email_service = email_service

    def create_order(self, request: OrderRequest) -> OrderResponse:
        # 1. Fetch data from lower layer
        customer = self._customer_repo.get_by_id(request.customer_id)
        if not customer:
            raise ValueError("Customer not found.")

        # 2. Instantiate the Rich Domain Model
        order = Order(customer)

        # 3. Delegate business logic to the Rich Model
        for item_req in request.items:
            # SECURITY NOTE: We look up the item from the repository 
            # to get the true price, rather than trusting the price 
            # provided in the HTTP request DTO.
            actual_item = self._item_repo.get_by_id(item_req.item_id)
            if not actual_item:
                raise ValueError(f"Item {item_req.item_id} not found.")

            # Map the quantity from the request to the domain object
            actual_item.quantity = item_req.quantity

            # The service doesn't care about discount rules; 
            # the Order model handles that internally.
            order.add_item(actual_item)

        # 4. Send the updated model back down to Data Access
        self._order_repo.save(order)
        self._email_service.send(
            order.customer_email, "Confirmed!", "Success."
        )
        return OrderResponse(
            order_id=order.id,
            total_price=order.total_price,
            customer_email=order.customer_email
        )