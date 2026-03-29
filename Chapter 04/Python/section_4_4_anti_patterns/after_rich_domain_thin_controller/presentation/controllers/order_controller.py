from ...application.i_order_service import IOrderService
from ...application.order_request import OrderRequest

class OrderController:
    """
    THE THIN CONTROLLER
    ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
    Controller" anti-pattern. It has zero business logic, zero 
    database logic, and zero validation rules. Its ONLY job is to 
    translate an HTTP POST request into a Business Logic method call, 
    and return a standard data structure.
    """
    def __init__(self, order_service: IOrderService):
        self._order_service = order_service

    def create_order(self, request: OrderRequest):
        # The service returns an OrderResponse DTO
        # If any business rules fail, the exception bubbles up to demo.py
        response = self._order_service.create_order(request)

        # Return a standard Python dictionary! 
        return {
            "OrderId": response.order_id,
            "TotalPrice": response.total_price,
            "CustomerEmail": response.customer_email
        }