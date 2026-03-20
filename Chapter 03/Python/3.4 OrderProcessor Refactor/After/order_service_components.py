# --- Step 1: The Individual Service Classes ---

# Handles only validation logic
class OrderValidator:
    def validate(self, order):
        print("  [Validate] Validating order...")
        if not order.items or order.total <= 0:
            raise ValueError("Order is invalid.")

# Handles only payment processing
class PaymentService:
    def process_payment(self, order):
        print(f"  [Payment] Processing payment for ${order.total:.2f}...")
        # Real payment gateway logic would go here
        return True

# Handles only inventory updates
class InventoryManager:
    def update_inventory(self, order):
        print("  [Inventory] Updating inventory...")
        # Real database logic to update stock would go here

# Handles only sending notifications
class NotificationService:
    def send_confirmation_email(self, order):
        print(f"  [Notify] Sending confirmation email to {order.customer_email}...")
        # Real email sending logic would go here