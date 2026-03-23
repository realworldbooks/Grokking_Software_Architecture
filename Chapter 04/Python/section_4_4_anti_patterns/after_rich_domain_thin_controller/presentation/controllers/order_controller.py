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

    def create_order(self, request: OrderRequest) -> str:
        # Controller simply delegates work to the layer below it
        order_id = self._order_service.create_order(request)
        
        # Controller formats the HTTP response
        return json.dumps({"OrderId": order_id})