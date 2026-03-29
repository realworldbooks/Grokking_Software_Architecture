/**
 * Represents a single architectural choice to be evaluated.
 * * ARCHITECTURAL NOTE: Data Structures
 * By keeping this class purely for data, we can easily serialize it, 
 * deserialize it from a database, or pass it around without dragging 
 * any heavy calculation logic along with it.
 */
class Option {
    /**
     * @param {string} name - e.g., "Redis", "In-Memory Cache"
     * @param {Object.<string, number>} scores - e.g., { "performance": 5, "cost": 2 }
     */
    constructor(name, scores) {
        this.name = name;
        this.scores = scores;
    }
}

module.exports = Option;