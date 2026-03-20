from abc import ABC, abstractmethod

class Player(ABC):
    @abstractmethod
    def play_field_position(self):
        pass