# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/presentation/controllers/order_controller.py
import json
from ...application.i_order_service import IOrderService
from ...application.order_request import OrderRequest

class OrderController:
    """
    THE THIN CONTROLLER
    ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
    Controller" anti-pattern. It has zero business logic, zero 
    database logic, and zero validation rules. Its ONLY job is to 
    translate an HTTP POST request into a Business Logic method call, 
    and return an HTTP response (200 OK).
    """
    def __init__(self, order_service: IOrderService):
        self._order_service = order_service

    def create_order(self, request: OrderRequest):
       try:
            # 'response' is now an OrderResponse DTO, not just an ID
            # This matches the C# return Ok(response) logic
            response = self._order_service.create_order(request)

            # Return the object as a flat JSON structure for the API
            return json.dumps({
                "OrderId": response.order_id,
                "TotalPrice": response.total_price,
                "CustomerEmail": response.customer_email
            })
       except Exception as ex:
            # Matches the C# BadRequest(ex.Message) logic
            return json.dumps({"Error": str(ex)}), 400