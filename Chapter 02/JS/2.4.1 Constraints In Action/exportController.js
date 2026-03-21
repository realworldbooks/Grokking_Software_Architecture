const db = require('./database');

/**
 * Simulates a "Controller" or route handler in a web framework like Express.js.
 * Its primary responsibility is to handle an incoming web request, orchestrate
 * the necessary business logic, and then format and send a proper web response.
 * 
 * @param {object} req - The simulated Express request object.
 * @param {object} res - The simulated Express response object.
 */
async function exportUserDataRoute(req, res) {
    try {
        // In a real app, the user ID would likely come from a session or token
        // that has been validated by middleware.
        const userId = req.user.id;

        // 1. ORCHESTRATION & ASYNC CONSTRAINT: The controller calls other services.
        // In Node.js, I/O operations like database calls are asynchronous. The `await`
        // keyword is a technical constraint of the language used to handle this.
        const userData = await db.fetchUserData(userId);

        // 2. BUSINESS CONSTRAINT: Handle the case where the user does not exist.
        // The business rule is "if a user is not found, report it clearly."
        // The technical implementation is to return an HTTP 404 Not Found status.
        if (!userData) {
            // By returning here, we stop execution and send the 404 response immediately.
            return res.status(404).send('User not found.');
        }

        // 3. TECHNICAL CONSTRAINT: The data must be formatted as CSV.
        const headers = 'id,name,email\\n';
        const csvRow = `${userData.id},${userData.name},${userData.email}\\n`;
        const csvData = headers + csvRow;

        // 4. TECHNICAL CONSTRAINT: The response must adhere to the HTTP protocol.
        // We set headers to tell the browser this is a CSV file for download.
        res.setHeader('Content-Type', 'text/csv');
        res.setHeader(
            'Content-Disposition', 
            `attachment; filename="user_data_${userId}.csv"`
        );
        // We end the request with a 200 OK status and the CSV data as the body.
        res.status(200).end(csvData);

    } catch (error) {
        // 5. BUSINESS/TECHNICAL CONSTRAINT: Handle unexpected errors gracefully.
        // The system should not crash. It should log the error for developers
        // and return a generic server error (HTTP 500) to the user.
        console.error('Export failed:', error);
        res.status(500).send('An error occurred during export.');
    }
}

module.exports = exportUserDataRoute;