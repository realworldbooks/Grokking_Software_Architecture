/**
 * This is a "Fake" or "Mock" implementation of our database connection.
 * It's a "Test Double," a stand-in for the real thing used exclusively 
 * in a testing context. It returns predictable, hardcoded data so we 
 * can verify the behavior of the ReportGenerator in isolation.
 */
class FakeDatabaseConnection {
    /**
     * Returns a predictable list of strings for testing.
     * @param {string} query 
     * @returns {string[]}
     */
    getData(query) {
        // For our test, we'll just return a list with a known number of items (3).
        return ["fake_row1", "fake_row2", "fake_row3"];
    }
}

module.exports = FakeDatabaseConnection;