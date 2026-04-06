from .some_repository import SomeRepository
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
        LogManager.info("Demo", "--- Running 'Before' (Upward Dep) ---")

        # 1. Instantiate the low-level detail
        before_repo = SomeRepository()

        # 2. Execute the business logic (which directly uses the low-level detail)
        before_repo.update_data(123, "New Data")

        LogManager.info("Demo", "------------------------------------")
