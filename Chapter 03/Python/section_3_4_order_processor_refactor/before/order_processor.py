"""
ANTI-PATTERN: The "God Class" / Monolithic Script.
* ARCHITECTURE PROBLEM: This class is the "Everything Hub." It violates 
the Single Responsibility Principle (SRP) by managing four distinct 
responsibilities:
1. Validation Logic
2. Payment Gateway Integration
3. Inventory State Management
4. Notification / Communication Logic

WHY THIS IS BRITTLE:
- Hard to Test: You can't test if the validation logic works without 
  triggering print statements for payments and emails.
- High Coupling: If you switch from Email to SMS notifications, you have 
  to modify this core business processing file.
- Fragile: A failure in the "Notify" step could theoretically crash 
  the entire order process if not handled correctly.
"""

class OrderProcessor:
    def process(self, order: 'Order'):
        # 🚨 ARCHITECTURE WARNING: Responsibility 1 - Validation.
        # This logic should be encapsulated in a dedicated validator.
        print("  [Validate] Validating order...")
        if not order.items or order.total <= 0:
            raise ValueError("Order is invalid.")

        # 🚨 ARCHITECTURE WARNING: Responsibility 2 - Payment.
        # This couples the high-level flow to specific billing logic.
        print(f"  [Payment] Processing payment for ${order.total:.2f}...")
        payment_success = True

        if payment_success:
            # 🚨 ARCHITECTURE WARNING: Responsibility 3 - Inventory.
            # Direct dependency on infrastructure/database concerns.
            print("  [Inventory] Updating inventory...")

            # 🚨 ARCHITECTURE WARNING: Responsibility 4 - Notifications.
            # Direct dependency on external communication services.
            print(f"  [Notify] Sending confirmation email to {order.customer_email}...")
            
            return "Order processed successfully."
        else:
            return "Payment failed."