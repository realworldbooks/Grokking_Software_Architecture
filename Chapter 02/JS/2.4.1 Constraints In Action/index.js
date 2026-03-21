const exportUserDataRoute = require('./exportController');

/**
 * A lightweight mock of an Express.js Response object for console output.
 * This "Test Double" allows us to see how our route handler tries to manipulate
 * the response object without needing to run a full web server. It's a way to
 * isolate and demonstrate the controller's logic.
 */
class MockResponse {
    status(code) {
        this.statusCode = code;
        // Return `this` to allow for chaining, e.g., `res.status(200).send(...)`
        return this;
    }
    setHeader(key, value) {
        console.log(`  [Headers] ${key}: ${value}`);
    }
    send(message) {
        console.log(`  [HTTP ${this.statusCode}] ${message}`);
    }
    end(data) {
        console.log(`  [HTTP ${this.statusCode}] OK`);
        console.log("\\n--- File Body ---");
        process.stdout.write(data);
        console.log("-----------------");
    }
}

/**
 * This file acts as a simple simulator for the export route.
 * It demonstrates how the controller responds to different requests.
 */
async function runConstraintsDemo() {
    console.log("--- Constraints In Action Example ---");

    // SCENARIO 1: A valid request for an existing user.
    // We expect a CSV file and an HTTP 200 OK status.
    console.log("\\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]");
    const req1 = { user: { id: "User123" } };
    const res1 = new MockResponse();
    await exportUserDataRoute(req1, res1);

    // SCENARIO 2: A request for a user who does not exist.
    // We expect an error message and an HTTP 404 Not Found status.
    console.log("\\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]");
    const req2 = { user: { id: "UnknownUser" } };
    const res2 = new MockResponse();
    await exportUserDataRoute(req2, res2);

    console.log("\\n-------------------------------------\\n");
}

runConstraintsDemo();