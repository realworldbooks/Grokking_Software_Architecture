from order_repository import OrderRepository
from order import Order

class SqlOrderRepository(OrderRepository):
    """
    DATA ACCESS LAYER (LOW-LEVEL DETAIL).
    ARCHITECTURE NOTE: This is a concrete implementation. 
    It 'plugs into' the architecture by fulfilling the 
    OrderRepository contract.
    """
    def save(self, order: Order):
        print("(After Refactor) Saving order to SQL...")