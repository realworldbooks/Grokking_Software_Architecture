from abc import ABC, abstractmethod

class Player(ABC):
    @abstractmethod
    def perform_action(self):
        pass