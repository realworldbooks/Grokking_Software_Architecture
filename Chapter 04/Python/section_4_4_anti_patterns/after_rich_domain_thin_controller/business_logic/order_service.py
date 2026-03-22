# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/order_service.py
from .i_order_service import IOrderService
from .order_request import OrderRequest
from ..domain_models.order import Order

# THE DOWNWARD DEPENDENCY
from ..data_access.data_access_interfaces import (
    IOrderRepository,
    ICustomerRepository,
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
        email_service: IEmailService
    ):
        # Dependencies on the Data Access layer below it
        self._order_repo = order_repo
        self._customer_repo = customer_repo
        self._email_service = email_service

    def create_order(self, request: OrderRequest) -> int:
        # 1. Fetch data from lower layer
        customer = self._customer_repo.get_by_id(request.customer_id)
        if not customer:
            raise ValueError("Not found.")

        # 2. Instantiate the Rich Domain Model
        order = Order(customer.email)

        # 3. Delegate business logic to the Rich Model
        for item in request.items:
            # The service doesn't care about discount rules; 
            # the Order model handles that internally.
            order.add_item(item, customer)

        # 4. Send the updated model back down to Data Access
        self._order_repo.save(order)
        self._email_service.send(
            order.customer_email, "Confirmed!", "Success."
        )

        return order.id