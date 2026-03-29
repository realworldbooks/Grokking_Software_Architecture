from .order_repository import OrderRepository
from .order import Order

class OrderService:
    """
    BUSINESS LOGIC LAYER (HIGH-LEVEL POLICY).
    ARCHITECTURE NOTE: This service is 'UI-Ignorant' and 
    'DB-Ignorant'. It only knows about the Repository 
    abstraction. This follows the Downward Dependency rule.
    """
    def __init__(self, repo: OrderRepository):
        # Dependency Injection: We receive the tool we need.
        self.repo = repo 

    def save_order(self, order: Order):
        # We call DOWNWARD into the abstraction.
        self.repo.save(order)