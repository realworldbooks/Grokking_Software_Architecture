from abc import ABC, abstractmethod

"""
ISP SOLUTION: Role-Based Abstractions.
* ARCHITECTURE NOTE: We have split the bloated TrainingSession into two 
focused interfaces. This follows the principle that "No client should be 
forced to depend on methods it does not use."
"""

class FieldPlayerTraining(ABC):
    @abstractmethod
    def practice_shooting(self): pass
    
    @abstractmethod
    def practice_tackling(self): pass

class GoalieTraining(ABC):
    @abstractmethod
    def practice_diving_saves(self): pass
    
    @abstractmethod
    def practice_hand_distribution(self): pass