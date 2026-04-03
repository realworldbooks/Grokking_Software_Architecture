/**
 * The Core Business Logic.
 * WARNING: This class is a "Liability" because it violates the 
 * Golden Rule of Separation of Concerns.
 */
class ServerMonitor {
    /**
     * Checks the server temperature and sends an alert if it's too high.
     * @param {number} temp - The current temperature reading.
     */
    checkTemperature(temp) {
        // VIOLATION: Hardcoded "magic number". 
        // This should be a configurable threshold.
        if (temp > 95) {
            // VIOLATION: Tight Coupling.
            // We are 'new-ing' up a concrete dependency inside our logic.
            // This makes the class impossible to unit test without a live API.
            const twilio = new TwilioClient("API_KEY");
            twilio.sendSms("555-1234", "Server is overheating!");
        } else {
            console.log(`Temp ${temp} is nominal.`);
        }
    }
}

/**
 * Mock of a 3rd party SMS library.
 * In a real system, this is the "Chaotic Outside World".
 */
class TwilioClient {
    constructor(key) {
        this.key = key;
    }
    
    sendSms(to, body) {
        console.log(`[Twilio API] Sending SMS to ${to}: ${body}`);
    }
}

module.exports = ServerMonitor;