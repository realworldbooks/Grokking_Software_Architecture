// JavaScript doesn't have native Interfaces, but we simulate the architectural behavior by defining the classes exactly 
// how they look conceptually!
class Midfielder {
    practiceShooting() {
        console.log("  [Midfielder] Practicing shooting drills.");
    }

    practiceTackling() {
        console.log("  [Midfielder] Practicing slide tackles.");
    }

    // Forced to include these by the "Fat Interface" design
    practiceDivingSaves() {
        throw new Error("Midfielders don't play in the net!");
    }

    practiceHandDistribution() {
        throw new Error("Midfielders can't use their hands!");
    }
}

module.exports = Midfielder;