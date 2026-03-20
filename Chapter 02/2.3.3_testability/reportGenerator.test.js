// Import both classes to be tested
const { ReportGeneratorBefore, ReportGeneratorAfter } = require('./reportGenerator');

// This is a new test to demonstrate the problem with the "Before" version
test('Generate_Report_Fails_Unit_Test (Bad Way)', () => {
  // We can't inject a fake database. This test is now coupled
  // to the REAL DatabaseConnection class. This makes it an
  // integration test, not a unit test. It has side effects
  // (like console.logs) and can be slow.
  const reportGenerator = new ReportGeneratorBefore();

  // Act
  const result = reportGenerator.generate("FailingTestReport");

  // We can still assert the result, but we can't control the
  // underlying data source, which makes the test brittle. If the
  // real database changed, this test would break.
  //
  // The assertion below WILL FAIL because the "real" database
  // connection returns 2 rows, but we want to test a 3-row case.
  // We have no way to force a 3-row result for our test.
  expect(result).toBe("Report 'FailingTestReport' generated with 3 rows.");
});

// This is the Jest test from the book for the "After" version
test('Generate_Report_With_Fake_Data (Good Way)', () => {
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

