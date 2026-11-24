from abc import ABC, abstractmethod

"""
THE ARCHITECTURAL INTERFACE.
* ARCHITECTURE NOTE: This Abstract Base Class (ABC) is the "Inversion" point. 
Both the high-level Coach and the low-level Players now depend on this 
abstraction. This creates a "seam" in the code, allowing us to swap 
implementations without breaking the coordinator.
"""
class Player(ABC):
    @abstractmethod
    def perform_action(self):
        """
        The shared contract that all team members must fulfill.
        """
        pass