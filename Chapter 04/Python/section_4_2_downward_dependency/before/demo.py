from .some_repository import SomeRepository

class Demo:
        
    @staticmethod
    def run():
        """
        THE COMPOSITION ROOT.
        ARCHITECTURE NOTE: This is the only place where we 
        pair the High-Level Service with the Low-Level SQL 
        implementation.
        """
        print("--- Running 'Before' (Upward Dep) ---")

        # 1. Instantiate the low-level detail
        before_repo = SomeRepository()

        # 2. Execute the business logic (which directly uses the low-level detail)
        before_repo.update_data(123, "New Data")

        print("------------------------------------")
