"""
Represents a single architectural choice to be evaluated.
ARCHITECTURAL NOTE: Data Structures
By keeping this class purely for data, we can easily serialize it 
(e.g., to JSON) without dragging along any heavy calculation logic.
"""

class Option:
    def __init__(self, name: str, scores: dict[str, int]):
        """
        :param name: The name of the option (e.g., "Redis")
        :param scores: Dictionary of criteria and their scores (1-5)
        """
        self.name = name
        self.scores = scores