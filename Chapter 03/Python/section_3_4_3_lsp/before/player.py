from abc import ABC, abstractmethod

"""
THE BASE CONTRACT.
* ARCHITECTURE NOTE: This Abstract Base Class establishes a baseline 
expectation: "Any object that identifies as a Player MUST be able to 
execute play_field_position() successfully." 
"""
class Player(ABC):
    @abstractmethod
    def play_field_position(self):
        """
        🚨 ARCHITECTURE WARNING: We are establishing a requirement 
        that all subclasses are expected to fulfill.
        """
        pass