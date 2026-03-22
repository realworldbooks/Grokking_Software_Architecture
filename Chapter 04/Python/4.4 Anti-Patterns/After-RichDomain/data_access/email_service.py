# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/data_access/email_service.py
from .data_access_interfaces import IEmailService
# Note: This is a concrete implementation of the IEmailService
# interface. It simulates sending an email via SMTP.

class SmtpEmailService(IEmailService):
    """
    Simulates sending an order confirmation email via SMTP.
    """
    def send_order_confirmation(self, order):
        customer_name = order.get_customer().name
        total_price = order.total_price
        
        # In a real system, you would connect to an SMTP server
        # and send a formatted email.
        print("---")
        print(f"Connecting to SMTP server...")
        print(f"Sending email to {customer_name}:")
        print(f"Subject: Your Order Confirmation")
        print(f"Body: Thank you for your order of ${total_price:.2f}.")
        print("Email sent.")
        print("---")
