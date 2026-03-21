"""
THE CONSUMER (The Victim of the LSP Violation).
* ARCHITECTURE PROBLEM: The Coach trusts the abstraction of "Player". 
The Coach assumes that if they are handed a Player, calling 
play_field_position() will result in a field-based action. 
* Because the Goalie was substituted where a field player was 
expected, the Coach's game plan fails. In a larger system, this 
leads to "Special Case" checks (if isinstance(player, Goalie)...) 
which is an architectural red flag.
"""
class Coach:
    def direct_field_play(self, field_player):
        print("  [Coach] Alright player, execute your field assignment!")
        
        # 🚨 If a Goalie is passed here, the logic "breaks" conceptually 
        # because the behavior is incompatible with the intent of the call.
        field_player.play_field_position()