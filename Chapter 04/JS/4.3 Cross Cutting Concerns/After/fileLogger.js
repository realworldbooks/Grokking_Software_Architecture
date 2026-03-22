/**
 * A concrete implementation of the logging protocol.
 */
class FileLogger {
    log(message) {
        console.log(`(AFTER_LOGGER) File Log: ${message}`);
    }
}
module.exports = FileLogger;