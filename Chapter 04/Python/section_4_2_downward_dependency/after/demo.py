from .order import Order
from .order_service import OrderService
from .sql_order_repository import SqlOrderRepository

class Demo:


    @staticmethod
    def run():
        """
        THE COMPOSITION ROOT.
        ARCHITECTURE NOTE: This is the only place where we
        pair the High-Level Service with the Low-Level SQL
        implementation.
        """
        print("--- Running 'After' (Downward Dep) ---")

        # 1. Instantiate the low-level detail
        after_repo = SqlOrderRepository()

        # 2. Inject it into the high-level service
        after_service = OrderService(after_repo)
        
        # 3. Execute the business logic
        after_service.save_order(Order())
        
        print("--------------------------------------")
