/**
 * PRIMARY PORT (Driven).
 * Defines the contract that all infrastructure adapters must follow.
 * This ensures the Core remains agnostic of specific 3rd-party implementations.
 */
class AlertPort {
    /**
     * Sends an alert message to an external destination.
     * @param {string} message - The content of the alert.
     * @throws {Error} If the subclass does not implement this method.
     */
    sendAlert(message) {
        throw new Error("Method 'sendAlert(message)' must be implemented by the Adapter.");
    }
}

module.exports = AlertPort;