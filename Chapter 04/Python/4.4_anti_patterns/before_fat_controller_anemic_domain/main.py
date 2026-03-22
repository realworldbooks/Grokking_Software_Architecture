from order_controller import OrderController

class MockRequest:
    """Simulates an incoming web request object."""
    def __init__(self, items, customer_type, customer_email):
        self.items = items
        self.customer_type = customer_type
        self.customer_email = customer_email

def main():
    """
    ENTRY POINT.
    ARCHITECTURE NOTE: The 'Fat Controller' logic is triggered 
    here. Because the controller creates its own database 
    connections and email services, we have no way to 
    intercept or mock them in this main script.
    """
    print("--- Chapter 4: Fat Controller (Before) ---")

    controller = OrderController()

    # Creating a mock request with 'Gold' status
    request = MockRequest(
        items=[{"name": "Monitor", "price": 300, "qty": 2}],
        customer_type="Gold",
        customer_email="python@arch.org"
    )

    result = controller.create_order(request)
    print(f"Controller Response: {result}")
    
    print("-------------------------------------------")

if __name__ == "__main__":
    main()