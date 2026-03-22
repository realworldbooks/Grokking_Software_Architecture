/**
 * A simple class to hold an option and its scores.
 * Scores are from 1 (bad) to 5 (good) for each criterion.
 */
class Option {
    constructor(name, scores) {
        this.name = name;
        this.scores = scores; // e.g., { availability: 1, performance: 5, simplicity: 5 }
    }
}

module.exports = Option;