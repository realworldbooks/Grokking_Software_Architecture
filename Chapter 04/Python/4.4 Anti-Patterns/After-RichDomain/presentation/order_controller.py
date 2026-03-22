# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/presentation/order_controller.py
from ..business_logic.order_service import OrderService
from ..business_logic.order_request import OrderRequest

class OrderController:
    """
    The Presentation Layer. In a web app, this would be the
    API endpoint that receives HTTP requests.

    This controller is now "Thin". Its only job is to translate
    the incoming request into a DTO, delegate the work to the
    business layer (OrderService), and return a response. It
    contains no business logic itself.
    """
    def __init__(self, order_service: OrderService):
        self._order_service = order_service

    def create_order(self, raw_request):
        """
        Simulates handling an HTTP POST request.
        'raw_request' would be the JSON body.
        """
        print("---")
        print("Controller: Received request.")
        
        # Translate raw request to a DTO
        order_request = OrderRequest(
            customer_id=raw_request['customer_id'],
            items=raw_request['items']
        )

        # Delegate to the business logic layer
        order = self._order_service.create_order(order_request)

        # Return a response (e.g., HTTP 201 Created)
        print("Controller: Responding with success.")
        print("---")
        return {"order_id": order.get_customer().id, "total": order.total_price}
