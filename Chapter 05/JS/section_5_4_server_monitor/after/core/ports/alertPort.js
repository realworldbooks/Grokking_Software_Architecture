/**
 * PRIMARY PORT (Driven).
 * Since JavaScript lacks native interfaces, we use this base class 
 * to define the 'Contract'. If an Adapter fails to implement sendAlert, 
 * the application will throw a clear architectural error.
 */
class AlertPort {
    /**
     * Sends an alert message to an external destination.
     * @param {string} message 
     */
    sendAlert(message) {
        throw new Error("Method 'sendAlert(message)' must be implemented by the Adapter.");
    }
}

module.exports = AlertPort;