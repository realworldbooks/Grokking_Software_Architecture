const AlertPort = require('../../core/ports/alertPort');
const { TwilioClient } = require('../externalLibs/fakeLibs');

/**
 * ADAPTER 1: The "Real" Production Adapter.
 * This class is the bridge between the internal AlertPort and the external Twilio API.
 */
class TwilioAdapter extends AlertPort {
    /**
     * Configuration is injected here, keeping 'God Mode' keys out of the Core.
     */
    constructor(apiKey, targetPhoneNumber) {
        super();
        this.apiKey = apiKey;
        this.targetPhoneNumber = targetPhoneNumber;
    }

    sendAlert(message) {
        // We encapsulate the 'Chaotic' 3rd party SDK here.
        const client = new TwilioClient(this.apiKey);
        client.sendSms(this.targetPhoneNumber, message);
        console.log(`(PROD ADAPTER) SMS sent via Twilio: ${message}`);
    }
}

module.exports = TwilioAdapter;