/**
 * ANTI-PATTERN: GLOBAL LOGGING UTILITY.
 * ARCHITECTURE NOTE: Exporting a singleton or static method 
 * creates a rigid dependency for any module that imports it.
 */
class StaticFileLogger {
    static log(message) {
        console.log(`(BEFORE_LOGGER) Static Log: ${message}`);
    }
}

module.exports = StaticFileLogger;