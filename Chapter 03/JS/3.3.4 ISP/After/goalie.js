/**
 * INTERFACE COMPOSITION (The Goalie "Opt-In").
 * * ARCHITECTURE NOTE: The beauty of Interface Segregation is that classes 
 * can "opt-in" to the specific behaviors they need. Since a Goalie needs 
 * both field skills (kicking/tackling) and specialized net skills, it 
 * simply implements both conceptual roles. 
 * * We have accommodated the complex requirements of the Goalie without 
 * polluting the Midfielder's codebase with irrelevant methods!
 */
class Goalie {
    /**
     * Implements Field Player logic.
     */
    practiceShooting() {
        console.log("  [Goalie] Practicing goal kicks and long shots.");
    }

    /**
     * Implements Field Player logic.
     */
    practiceTackling() {
        console.log("  [Goalie] Practicing 1-on-1 box tackles.");
    }

    /**
     * Implements Goalie-specific logic.
     */
    practiceDivingSaves() {
        console.log("  [Goalie] Practicing top-corner diving saves.");
    }

    /**
     * Implements Goalie-specific logic.
     */
    practiceHandDistribution() {
        console.log("  [Goalie] Practicing fast break throws.");
    }
}

module.exports = Goalie;