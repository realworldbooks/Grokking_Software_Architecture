"""
ANTI-PATTERN: The "God Class" (SRP Violation).

ARCHITECTURE PROBLEM: This class is doing way too much. According to the 
Single Responsibility Principle, a class should have only ONE reason to change. 

This Python implementation currently has three distinct reasons:
1. Domain Logic: If the physical rules of how a player dribbles change.
2. Tactical Logic: If the AI algorithm for positioning is updated.
3. Persistence Logic: If the database schema or the way we save data changes.

In a professional Python environment, this makes unit testing a nightmare 
because you cannot test a simple 'dribble' without the class carrying around 
unused (and potentially broken) database code.
"""

class Player:
    def __init__(self, name: str):
        self.name = name

    # Responsibility 1: Player’s own state/abilities (Domain Logic)
    # This is the only logic that truly belongs inside this entity.
    def dribble_ball(self):
        print(f"  [Action] {self.name} is dribbling the ball down the court.")

    # 🚨 ARCHITECTURE WARNING: Tactical Logic (Should be in a Service)
    # This couples the Player to a specific AI strategy.
    def determine_best_position(self):
        print(f"  [Tactics] Calculating optimal court position for {self.name}...")

    # 🚨 ARCHITECTURE WARNING: Data Persistence (Should be in a Repository)
    # This couples the domain model to a specific infrastructure or database.
    def save_stats_to_database(self):
        print(f"  [Database] Saving {self.name}'s game stats to the database.")