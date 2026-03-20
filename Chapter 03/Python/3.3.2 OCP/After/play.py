from abc import ABC, abstractmethod

class Play(ABC):
    @abstractmethod
    def execute(self):
        pass