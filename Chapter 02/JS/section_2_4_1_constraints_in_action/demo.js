const ExportController = require('./exportController');

/**
 * Simulates requests to the ExportController to see constraints in action.
 */
class Demo {
    static async run() {
        console.log("--- Constraints In Action Example ---");

        // ARCHITECTURAL NOTE: We only interact with the Controller, 
        // respecting the layer boundaries of the application.
        const controller = new ExportController();

        // SCENARIO 1: Valid user.
        console.log("\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]");
        await controller.exportUserDataAsync("User123");

        // SCENARIO 2: Non-existent user.
        console.log("\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]");
        await controller.exportUserDataAsync("UnknownUser");

        console.log("\n-------------------------------------\n");
    }
}

module.exports = Demo;