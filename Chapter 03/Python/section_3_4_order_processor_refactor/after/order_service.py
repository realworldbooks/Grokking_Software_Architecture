"""
THE SOLUTION: The Coordinator / Facade.
* ARCHITECTURE NOTE: This class no longer contains "how-to" logic; it only 
* contains "when-to" logic. 
* By using Constructor Injection, we've achieved full Dependency Inversion (DIP). 
* This class doesn't care HOW an order is validated or HOW a payment is 
* processed; it simply coordinates the flow between its injected dependencies.
"""
class OrderService:
    """
    Dependencies are injected from the outside.
    """
    def __init__(self, validator, payment_service, inventory_manager, notification_service):
        self.validator = validator
        self.payment_service = payment_service
        self.inventory_manager = inventory_manager
        self.notification_service = notification_service

    """
    Orchestrates the high-level business process.
    """
    def process_order(self, order):
        # Step 1: Delegate validation
        self.validator.validate(order)

        # Step 2: Orchestrate the successful path
        if self.payment_service.process_payment(order):
            self.inventory_manager.update_inventory(order)
            self.notification_service.send_confirmation_email(order)
            return "Order processed successfully."
        else:
            return "Payment failed."