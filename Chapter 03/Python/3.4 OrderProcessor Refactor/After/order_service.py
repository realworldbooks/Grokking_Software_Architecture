class OrderService:
    def __init__(self, validator, payment_service, inventory_manager, notification_service):
        self.validator = validator
        self.payment_service = payment_service
        self.inventory_manager = inventory_manager
        self.notification_service = notification_service

    def process_order(self, order):
        self.validator.validate(order)

        if self.payment_service.process_payment(order):
            self.inventory_manager.update_inventory(order)
            self.notification_service.send_confirmation_email(order)
            return "Order processed successfully."
        else:
            return "Payment failed."