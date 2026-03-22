"""
INFRASTRUCTURE LAYER: EMAIL SERVICE.
ARCHITECTURE NOTE: Tightly coupling this service to the 
Controller makes unit testing impossible.
"""
class SmtpEmailService:
    def send(self, email: str, message: str):
        print(f"  [Email] SMTP: Sending '{message}' to {email}")