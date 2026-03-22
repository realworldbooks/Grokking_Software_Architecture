from play import Play

"""
OCP SOLUTION: Open for Extension.
* ARCHITECTURE NOTE: This class is the proof that the system is "Open for 
extension." We added a brand new behavior (a new play) by simply creating 
a new file. We didn't have to change a single line of code in the 
Midfielder class to make this work!
"""
class PassToStriker(Play):
    def execute(self):
        print("  [Action] Passing the ball to the striker!")