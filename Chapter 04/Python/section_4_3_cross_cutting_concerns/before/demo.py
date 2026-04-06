from order import Order
from order_service import OrderService
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
    LogManager.info("Demo", "--- Running 'Before' (Static Logger) ---")
    
    # The service is instantiated without any visible logger.
    before_service = OrderService()
    before_service.save_order(Order())
    
    LogManager.info("Demo", "-----------------------------------------")