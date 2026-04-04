/**
 * SHARED UTILITY.
 * Centralizes logging to ensure consistent formatting across the chapter.
 */
const LogManager = {
    info: (context, message, ...params) => {
        const timestamp = new Date().toISOString();
        // Simple string replacement for {0}, {1} to match the Java style
        let formattedMsg = message;
        params.forEach((p, i) => {
            formattedMsg = formattedMsg.replace(`{${i}}`, p);
        });
        console.log(`[${timestamp}] [INFO] [${context}] ${formattedMsg}`);
    }
};

module.exports = LogManager;