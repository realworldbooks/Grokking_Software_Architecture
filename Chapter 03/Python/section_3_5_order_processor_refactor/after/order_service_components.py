"""
SRP SOLUTION: Service Isolation.
* ARCHITECTURE NOTE: Each class here represents a specific domain boundary. 
The validator only knows how to check data, the payment service only 
knows how to talk to a gateway, etc. 
* This isolation makes each component independently testable and reusable 
across different parts of the application.
"""

class OrderValidator:
    def validate(self, order):
        print("  [Validate] Validating order...")
        if not order.items or order.total <= 0:
            raise ValueError("Order is invalid.")

class PaymentService:
    def process_payment(self, order):
        print(f"  [Payment] Processing payment for ${order.total:.2f}...")
        return True

class InventoryManager:
    def update_inventory(self, order):
        print("  [Inventory] Updating inventory...")

class NotificationService:
    def send_confirmation_email(self, order):
        print(f"  [Notify] Sending confirmation email to {order.customer_email}...")