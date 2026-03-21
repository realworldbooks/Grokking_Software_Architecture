/**
 * A Low-Level Detail.
 * * ARCHITECTURE NOTE: This class now fulfills a generic "Player" contract. 
 * By standardizing the method name to .performAction(), we allow this 
 * class to be swapped seamlessly with any other player type.
 */
class Forward {
    performAction() {
        console.log("  [Action] Forward is attacking.");
    }
}

module.exports = Forward;