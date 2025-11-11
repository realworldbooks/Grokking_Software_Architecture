// Import the "After" class to be tested
const { ReportGeneratorAfter } = require('./reportGenerator');

// This is the Jest test from the book
test('Generate_Report_With_Fake_Data', () => {
  // Create a simple "mock" database connection for our test
  const fakeDb = {
    getData: (query) => ["row1", "row2", "row3"]
  };
  
  // Inject the fake dependency
  const reportGenerator = new ReportGeneratorAfter(fakeDb);

  // Act
  const result = reportGenerator.generate("TestReport");

  // Assert
  expect(result).toBe("(After) Report 'TestReport' generated with 3 rows.");
});