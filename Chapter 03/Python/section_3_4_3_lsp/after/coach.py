from .player import Player

"""
THE CONFIDENT CONSUMER.
* ARCHITECTURE NOTE: Because we strictly adhered to LSP, the Coach class 
becomes incredibly simple. We no longer need 'if' checks to see if a 
player is a Goalie (isinstance checks), nor do we need to worry about 
unexpected logic failures. 
* The Coach trusts the abstraction because the architecture is now honest.
"""
class Coach:
    def direct_field_play(self, field_player: Player):
        """
        Directs the player to take their field position with absolute certainty.
        """
        print("  [Coach] Alright player, execute your field assignment!")
        
        # This call is now safe, predictable, and architecturally sound.
        field_player.play_field_position()