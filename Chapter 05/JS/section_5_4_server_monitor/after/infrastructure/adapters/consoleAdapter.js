const AlertPort = require('../../core/ports/alertPort');

/**
 * ADAPTER 2: The "Dev" Adapter.
 * Proves to Archie that the Core doesn't care if the alert goes to a 
 * cloud messaging service or simply prints to the local screen.
 */
class ConsoleAdapter extends AlertPort {
    sendAlert(message) {
        // We use ANSI escape codes to mimic a real red alert,
        // but the Core logic remains completely unaware of this UI detail.
        const ANSI_RED = "\x1b[31m";
        const ANSI_RESET = "\x1b[0m";
        console.log(`${ANSI_RED}(DEV ADAPTER) ALERT: ${message}${ANSI_RESET}`);
    }
}

module.exports = ConsoleAdapter;