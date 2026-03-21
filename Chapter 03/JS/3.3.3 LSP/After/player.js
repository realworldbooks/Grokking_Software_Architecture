/**
 * THE STRICTLY ENFORCED CONTRACT.
 * * ARCHITECTURE NOTE: We have narrowed the scope of this base class to represent 
 * a "Field-Capable Player." By doing so, we ensure that the playFieldPosition 
 * method is a valid expectation for every single subclass. This is the 
 * foundation of Liskov Substitution—the base class makes a promise that 
 * the subclasses must keep.
 */
class Player {
    playFieldPosition() {
        throw new Error("playFieldPosition() must be implemented by subclass.");
    }
}

module.exports = Player;