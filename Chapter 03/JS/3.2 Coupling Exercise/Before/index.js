const UserReportGenerator = require('./userReportGenerator');

console.log("=== Chapter 3: Coupling Test (BEFORE) ===");
console.log("Notice how many 'chatty' calls the client has to make!\n");

const generator = new UserReportGenerator();
const result = generator.generateReport(1);

console.log(`\nRESULT: ${result}`);
console.log("=========================================\n");