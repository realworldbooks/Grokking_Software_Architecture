from abc import ABC, abstractmethod

"""
THE STRICTLY ENFORCED CONTRACT.
* ARCHITECTURE NOTE: We have narrowed the scope of this base class to represent 
a "Field-Capable Player." By doing so, we ensure that the play_field_position 
method is a valid expectation for every single subclass. This is the 
foundation of Liskov Substitution—the base class makes a promise that 
the subclasses must keep.
"""
class Player(ABC):
    @abstractmethod
    def play_field_position(self):
        """
        Contract: Every subclass must implement a valid field-based action.
        """
        pass