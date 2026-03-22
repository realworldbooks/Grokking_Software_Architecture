from order import Order
from order_service import OrderService
from file_logger import FileLogger

def main():
    print("--- Running 'After Refactoring' (Injected Logger) ---")
    
    # Dependencies are created and injected at the start
    logger = FileLogger()
    after_service = OrderService(logger)
    
    after_service.save_order(Order())
    print("--------------------------------------------")

if __name__ == "__main__":
    main()