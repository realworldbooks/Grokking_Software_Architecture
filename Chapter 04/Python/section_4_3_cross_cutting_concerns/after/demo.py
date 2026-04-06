from .order import Order
from .order_service import OrderService
from .file_logger import FileLogger
from shared.log_manager import LogManager

class Demo:
    @staticmethod
    def run():
        """
        THE COMPOSITION ROOT.
        ARCHITECTURE NOTE: This is the only place where we
        pair the High-Level Service with the Low-Level SQL
        implementation.
        """
        LogManager.info("Demo", "--- Running 'After Refactoring' (Injected Logger) ---")

        # 1. Instantiate the low-level detail
        logger = FileLogger()

        # 2. Inject it into the high-level service
        after_service = OrderService(logger)
        
        # 3. Execute the business logic
        after_service.save_order(Order())
        
        LogManager.info("Demo", "--------------------------------------------")
