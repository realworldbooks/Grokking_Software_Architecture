
class OrderResponse:
    """
    DTO for the order creation response.
    """
    def __init__(self, order_id: int = 0, total_price: float = 0.0, customer_email: str = ""):
        self.order_id = order_id
        self.total_price = total_price
        self.customer_email = customer_email