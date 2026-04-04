const AlertPort = require('../../core/ports/alertPort');
const { TwilioClient } = require('../externalLibs/fakeLibs');

/**
 * THE ADAPTER (Production).
 * Bridges the domain's AlertPort to the external Twilio SMS SDK.
 * This class acts as the 'Clarity Engineer' between inside and outside.
 */
class TwilioAdapter extends AlertPort {
    /**
     * Initializes the adapter with infrastructure-specific configuration.
     * * @param apiKey The secret key used for authenticating with Twilio.
     * @param targetPhoneNumber The destination number for the SMS alert.
     */
    constructor(apiKey, targetPhoneNumber) {
        super();
        // Initializing the client once at the boundary for resource efficiency.
        this.client = new TwilioClient(apiKey);
        this.targetPhoneNumber = targetPhoneNumber;
    }

    /**
     * Implements the Port by mapping a domain message to an SDK call.
     * * @param message The alert text to be sent.
     */
    sendAlert(message) {
        this.client.sendSms(this.targetPhoneNumber, message);
        console.log(`(PROD ADAPTER) SMS sent via Twilio: ${message}`);
    }
}

module.exports = TwilioAdapter;