# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/business_logic/order_service.py
from ..domain_models.customer import Customer
from ..domain_models.item import Item
from ..domain_models.order import Order
from ..data_access.data_access_interfaces import (
    IOrderRepository, IEmailService
)
from .order_request import OrderRequest

class OrderService:
    """
    This service orchestrates the order creation process.
    It represents the Business Logic Layer.

    It solves the "Fat Controller" anti-pattern by moving
    orchestration logic out of the presentation layer and into
    this dedicated service.

    Note the downward dependency: this layer depends on both
    the Domain Models and the Data Access layers.
    """
    def __init__(
        self,
        order_repository: IOrderRepository,
        email_service: IEmailService
    ):
        self._order_repository = order_repository
        self._email_service = email_service

    def create_order(self, request: OrderRequest):
        # In a real system, you would fetch these from the DB
        customer = Customer(id=request.customer_id, name="John Doe", is_gold_member=True)
        
        # Create the rich domain model
        order = Order(customer)

        for req_item in request.items:
            # Fetch item from DB
            item = Item(id=req_item["item_id"], name="Sample Item", price=100.0)
            order.add_item(item, req_item["quantity"])

        # Use the data access layer to persist the order
        self._order_repository.save_order(order)

        # Use the data access layer to send a confirmation
        self._email_service.send_order_confirmation(order)

        return order
