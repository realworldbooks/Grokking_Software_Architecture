const UserReportGenerator = require('./userReportGenerator');

console.log("=== Chapter 3: Coupling Test (AFTER) ===");
console.log("Notice how clean and 'chunky' the interaction is now!\n");

const generator = new UserReportGenerator();
const result = generator.generateReport(1);

console.log(`\nRESULT: ${result}`);
console.log("========================================\n");