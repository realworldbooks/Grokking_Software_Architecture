from .order_repository import OrderRepository
from .order import Order
from shared.log_manager import LogManager

class SqlOrderRepository(OrderRepository):
    """
    DATA ACCESS LAYER (LOW-LEVEL DETAIL).
    ARCHITECTURE NOTE: This is a concrete implementation. 
    It 'plugs into' the architecture by fulfilling the 
    OrderRepository contract.
    """
    def save(self, order: Order):
        LogManager.info("SqlOrderRepository", "(After Refactor) Saving order to SQL...")