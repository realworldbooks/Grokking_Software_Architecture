/**
 * OCP SOLUTION: Closed for Modification.
 * * ARCHITECTURE NOTE: This class is now perfectly "Closed for modification." 
 * Notice that the previous if/else chain is completely GONE. 
 * * The Midfielder no longer cares WHAT the specific play is or how it functions. 
 * It only cares that the play object adheres to the "contract" by providing 
 * an execute() method. We can now add infinite new plays to the system 
 * without ever opening or editing this file again.
 */
class Midfielder {
    /**
     * Executes any play dynamically.
     * @param {Object} play - An object that must implement an execute() method.
     */
    executePlay(play) {
        // ARCHITECTURE NOTE: In JavaScript, we use 'Duck Typing' or explicit 
        // checks to ensure the object follows our expected contract.
        if (typeof play.execute !== 'function') {
            throw new Error("Play must implement an execute() method!");
        }
        
        // The Midfielder delegates the work to the Strategy object.
        play.execute();
    }
}

module.exports = Midfielder;