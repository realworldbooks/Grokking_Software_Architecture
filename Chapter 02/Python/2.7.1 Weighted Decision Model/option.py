from dataclasses import dataclass
from typing import Dict

# A simple structure to hold an option and its scores.
# Scores are from 1 (bad) to 5 (good) for each criterion.
@dataclass
class Option:
    name: str
    scores: Dict[str, int]  # e.g., {"speed": 5, "cost": 1, "simplicity": 4}