"""
A Low-Level Module.
* ARCHITECTURE NOTE: This class is a concrete implementation. Because the 
Coach imports it directly, any change to this class's interface will 
cause a ripple effect that breaks the Coach.
"""
class Forward:
    def attack(self):
        print("  [Action] Forward is attacking.")