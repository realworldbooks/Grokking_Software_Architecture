from abc import ABC, abstractmethod

"""
ANTI-PATTERN: The "Fat Interface" (ISP Violation).
* ARCHITECTURE PROBLEM: The TrainingSession ABC is trying to be everything 
to everyone. It bundles field player drills (shooting/tackling) with 
goalie-specific drills (diving/hands).
* This forces every player participating in a session to implement methods 
that are physically impossible or irrelevant for their role.
"""
class TrainingSession(ABC):
    @abstractmethod
    def practice_shooting(self): 
        """Valid for all players."""
        pass
    
    @abstractmethod
    def practice_tackling(self): 
        """Valid for field players."""
        pass
    
    @abstractmethod
    def practice_diving_saves(self): 
        """🚨 Goalie only!"""
        pass
    
    @abstractmethod
    def practice_hand_distribution(self): 
        """🚨 Goalie only!"""
        pass