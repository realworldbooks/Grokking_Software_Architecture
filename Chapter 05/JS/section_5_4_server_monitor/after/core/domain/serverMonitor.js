const Constants = require('./constants');

/**
 * THE INSIDE (The Core).
 * Contains the pure business rules for evaluating server health.
 * This class has zero knowledge of the transport layer (SMS, CLI, etc).
 */
class ServerMonitor {
    /**
     * Constructor Injection.
     * Links the business logic to a required output port.
     * * @param alertPort An adapter that satisfies the sendAlert contract.
     */
    constructor(alertPort) {
        this.alertPort = alertPort;
    }

    /**
     * Checks temperature against the single source of truth (Constants).
     * * @param temp The current temperature reading.
     */
    checkTemperature(temp) {
        if (temp > Constants.HIGH_TEMP_THRESHOLD) {
            // The Core defines 'What' happens; the Port handles 'How'.
            this.alertPort.sendAlert(`Temp is ${temp} degrees! Take cover!`);
        } else {
            console.log(`[Core] Temp ${temp} is normal.`);
        }
    }
}

module.exports = ServerMonitor;