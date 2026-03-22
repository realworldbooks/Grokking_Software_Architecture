from order import Order
from order_service import OrderService

def main():
    print("--- Running 'Before' (Static Logger) ---")
    
    # The service is instantiated without any visible logger.
    before_service = OrderService()
    before_service.save_order(Order())
    
    print("-----------------------------------------")

if __name__ == "__main__":
    main()