const AlertPort = require('../core/ports/alertPort');

/**
 * Fake port for isolated testing.
 * Extracted into its own file so it can be reused across multiple test suites.
 */
class FakeAlertPort extends AlertPort {
    constructor() {
        super();
        this.sentMessages = [];
    }

    sendAlert(message) {
        this.sentMessages.push(message);
    }
}

module.exports = FakeAlertPort;