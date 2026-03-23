from .data_access_interfaces import IEmailService

class SmtpEmailService(IEmailService):
    """
    ARCHITECTURE NOTE: By isolating Email logic here, we prevent 
    database concerns from "leaking" into the Presentation or 
    Business layers.
    """
    # Concrete implementation for an email provider
    def send(self, to: str, subject: str, body: str):
        pass