const getDashboardSummaryBefore = require('./dashboardBefore');
const getDashboardSummaryAfter = require('./dashboardAfter');

console.log("=== Chapter 2: Performance Example ===\n");

console.log("--- Running Before: Brute Force Query ---");
console.log("[Call: User logs in]");
getDashboardSummaryBefore("User123");

console.log("\n--- Running After: Smart Cache Architecture ---");
console.log("[Call 1: User logs in for the first time]");
getDashboardSummaryAfter("User999");

console.log("\n[Call 2: User refreshes the page a minute later]");
getDashboardSummaryAfter("User999");

console.log("\n======================================");