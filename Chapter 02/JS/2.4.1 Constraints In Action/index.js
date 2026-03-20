const exportUserDataRoute = require('./exportController');

// A lightweight mock of an Express Response object for console output
class MockResponse {
    status(code) {
        this.statusCode = code;
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
        console.log("\n--- File Body ---");
        process.stdout.write(data);
        console.log("-----------------");
    }
}

async function run() {
    console.log("=== Chapter 2: Constraints In Action Example ===\n");

    console.log("[Simulating GET /export-user-data for User123]");
    const req1 = { user: { id: "User123" } };
    const res1 = new MockResponse();
    await exportUserDataRoute(req1, res1);

    console.log("\n[Simulating GET /export-user-data for UnknownUser]");
    const req2 = { user: { id: "UnknownUser" } };
    const res2 = new MockResponse();
    await exportUserDataRoute(req2, res2);

    console.log("\n==============================================");
}

run();