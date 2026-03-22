/**
 * OCP SOLUTION: Open for Extension.
 * * ARCHITECTURE NOTE: This class proves that our system is "Open for extension." 
 * We added this brand new feature (a new play) simply by creating a new file. 
 * We didn't have to touch a single line of code in Midfielder.js to make 
 * this new behavior available!
 */
class PassToStriker {
    execute() {
        console.log("  [Action] Passing the ball to the striker!");
    }
}

module.exports = PassToStriker;