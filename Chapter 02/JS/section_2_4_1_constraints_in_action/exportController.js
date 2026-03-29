const Database = require('./database');

/**
 * Simulates a "Controller" in a web framework like Express.js.
 * It handles incoming requests, orchestrates logic, and formats responses.
 */
class ExportController {
    #db = new Database();

    /**
     * Simulates handling a GET request for user data export.
     * @param {string} userId 
     */
    async exportUserDataAsync(userId) {
        try {
            // 1. ORCHESTRATION: The controller calls the service to get data.
            const userData = await this.#db.fetchUserDataAsync(userId);

            // 2. BUSINESS CONSTRAINT: Handle the case where the user does not exist.
            // The technical implementation is to return an HTTP 404 status.
            if (!userData) {
                console.log("  [HTTP 404] User not found.");
                return; // Halt execution, enforcing the constraint.
            }

            // 3. TECHNICAL CONSTRAINT: Format the data as CSV.
            const headers = "id,name,email\n";
            const csvRow = `${userData.id},${userData.name},${userData.email}\n`;
            const csvData = headers + csvRow;

            // 4. TECHNICAL CONSTRAINT: Adhere to HTTP protocol headers.
            console.log("  [HTTP 200] OK");
            console.log("  [Headers] Content-Type: text/csv");
            console.log(`  [Headers] Content-Disposition: attachment; filename="user_data_${userId}.csv"`);
            console.log("\n--- File Body ---");
            process.stdout.write(csvData); // Equivalent to Console.Write
            console.log("-----------------");

        } catch (err) {
            // 5. BUSINESS/TECHNICAL CONSTRAINT: Error handling.
            // Catch unexpected exceptions and return an HTTP 500 status.
            console.log(`  [HTTP 500] Export failed: ${err.message}`);
        }
    }
}

module.exports = ExportController;