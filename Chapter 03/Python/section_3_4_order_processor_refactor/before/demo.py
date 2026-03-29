from order import Order
from order_processor import OrderProcessor

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: Order Processor (BEFORE) ===")
        print("One massive class handles everything...\n")

        order = Order(items=["Book", "Pen"], total=25.50, customer_email="customer@example.com")
        processor = OrderProcessor()
        
        result = processor.process(order)

        print(f"\nRESULT: {result}")
        print("===========================================\n")
