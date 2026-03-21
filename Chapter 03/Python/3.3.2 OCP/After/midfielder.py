"""
OCP SOLUTION: Closed for Modification.
* ARCHITECTURE NOTE: Look at the simplicity here! The massive if/elif chain 
is completely gone. The Midfielder no longer decides HOW a play works; 
it simply tells the play to execute itself. 
* This class is now "Closed for Modification"—we can add 100 new plays to 
the playbook and we will never have to touch this file again.
"""
class Midfielder:
    def execute_play(self, play: 'Play'):
        """
        Executes any play that follows the Play contract.
        """
        # The Midfielder delegates the work to the Strategy object.
        play.execute()