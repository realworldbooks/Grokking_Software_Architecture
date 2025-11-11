// --- "Before" Code ---

class DatabaseConnection {
  constructor(connectionString) { 
    // ... connects to a real DB ... 
    console.log(`(Simulation) Connecting to... ${connectionString}`);
  }
  
  getData(query) { 
    // ... fetches real data based on the query ... 
    console.log(`(Simulation) Running query: ${query}`);
    return ["real_data_row1", "real_data_row2"]; // (Simulated return)
  }
}

class ReportGenerator {
  constructor() {
    // #A This class is now permanently tied to a real database.
    this.dbConnection = new DatabaseConnection("live_connection_string");
  }

  generate(reportName) {
    const data = this.dbConnection.getData(reportName);
    // #B ... logic to format the report from data ...
    return `Report '${reportName}' generated with ${data.length} rows.`;
  }
}

// --- "After" Code ---

class ReportGeneratorAfter {
  constructor(dbConnection) {
    // The dependency is "injected" into the constructor.
    this.dbConnection = dbConnection;
  }

  generate(reportName) {
    const data = this.dbConnection.getData(reportName);
    return `(After) Report '${reportName}' generated with ${data.length} rows.`;
  }
}

// Export the classes for the test file
module.exports = { ReportGeneratorBefore, ReportGeneratorAfter };