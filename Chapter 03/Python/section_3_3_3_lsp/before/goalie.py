from player import Player

"""
ANTI-PATTERN: Violation of the Liskov Substitution Principle (LSP).
* ARCHITECTURE PROBLEM: LSP states that objects of a superclass should 
be replaceable with objects of its subclasses without breaking the 
application logic.
* Here, the Goalie inherits from Player but provides "surprising" 
behavior. It technically implements the method, but it refuses to 
perform the actual work promised by the base class.
"""
class Goalie(Player):
    def play_field_position(self):
        # 🚨 ARCHITECTURE WARNING: A goalie doesn't play the field! 
        # By allowing Goalie to be a subclass of Player, we are 
        # "lying" to any consumer (like the Coach) who expects a 
        # field-capable athlete.
        print("  [Goalie] I can't do that! I stay near the net and use my hands.")