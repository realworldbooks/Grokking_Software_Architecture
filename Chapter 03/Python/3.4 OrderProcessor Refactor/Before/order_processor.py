class OrderProcessor:
    def process(self, order):
        # 1. Validation
        print("  [Validate] Validating order...")
        if not order.items or order.total <= 0:
            raise ValueError("Order is invalid.")

        # 2. Payment Processing
        print(f"  [Payment] Processing payment for ${order.total:.2f}...")
        payment_success = True

        # 3. Inventory Update & 4. Confirmation Email
        if payment_success:
            print("  [Inventory] Updating inventory...")
            print(f"  [Notify] Sending confirmation email to {order.customer_email}...")
            return "Order processed successfully."
        else:
            return "Payment failed."