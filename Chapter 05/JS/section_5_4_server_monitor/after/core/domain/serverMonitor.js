const Constants = require('./constants');

/**
 * THE INSIDE (The Core).
 * This is the Pure Domain Logic. It has been 'Isolated' from the 
 * infrastructure. It contains zero references to Console, Twilio, or Kafka.
 */
class ServerMonitor {
    /**
     * Constructor Injection.
     * We "plug in" the adapter, allowing the Core to remain 
     * agnostic of the specific implementation.
     * * @param {Object} alertPort - An adapter that implements the sendAlert(message) contract.
     */
    constructor(alertPort) {
        this.alertPort = alertPort;
    }

    /**
     * Evaluates temperature against domain constants.
     * @param {number} temp 
     */
    checkTemperature(temp) {
        if (temp > Constants.HIGH_TEMP_THRESHOLD) {
            // The Core acts as the 'Boundary Keeper,' defining 'What' needs to 
            // happen, while leaving the 'How' to the outside world.
            this.alertPort.sendAlert(`Temp is ${temp} degrees! Take cover!`);
        } else {
            console.log(`[Core] Temp ${temp} is normal.`);
        }
    }
}

module.exports = ServerMonitor;