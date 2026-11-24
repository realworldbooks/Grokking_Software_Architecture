from abc import ABC, abstractmethod

class Logger(ABC):
    """
    1. THE ABSTRACTION (The Contract).
    """
    @abstractmethod
    def log(self, message: str):
        pass