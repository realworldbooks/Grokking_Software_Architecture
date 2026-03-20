from abc import ABC, abstractmethod

class TrainingSession(ABC):
    @abstractmethod
    def practice_shooting(self): pass
    
    @abstractmethod
    def practice_tackling(self): pass
    
    @abstractmethod
    def practice_diving_saves(self): pass
    
    @abstractmethod
    def practice_hand_distribution(self): pass