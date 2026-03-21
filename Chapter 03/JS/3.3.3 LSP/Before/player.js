/**
 * THE BASE CONTRACT.
 * * ARCHITECTURE NOTE: This class establishes a baseline expectation: 
 * "Any object that identifies as a Player MUST be able to execute 
 * playFieldPosition() successfully." 
 */
class Player {
    playFieldPosition() {
        // 🚨 ARCHITECTURE WARNING: We are establishing a requirement 
        // that all subclasses are expected to fulfill.
        throw new Error("playFieldPosition() must be implemented by subclass.");
    }
}

module.exports = Player;