from abc import ABC, abstractmethod

class AlertPort(ABC):
    """PRIMARY PORT (Driven).
    
    This Abstract Base Class defines the 'Contract' between the Domain 
    and the Infrastructure. It ensures that any adapter you plug 
    into the system is compatible with the Core's expectations.
    """
    
    @abstractmethod
    def send_alert(self, message: str) -> None:
        """Sends an alert message to an external destination.
        
        Args:
            message (str): The content of the alert to be dispatched.
            
        Raises:
            TypeError: If a concrete adapter fails to implement this method.
        """
        pass