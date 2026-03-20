class DatabaseConnection {
    constructor(connectionString) {
        console.log(`\n  [DB] Connecting to... ${connectionString}`);
    }
    getData(query) {
        return ["real_data_row1", "real_data_row2"];
    }
}

module.exports = DatabaseConnection;