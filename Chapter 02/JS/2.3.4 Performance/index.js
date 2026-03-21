const getDashboardSummaryBefore = require('./dashboardBefore');
const getDashboardSummaryAfter = require('./dashboardAfter');

function runPerformanceDemo() {
    console.log("--- Performance Example: Caching ---");
    const USER_ID = "user123";

    // --- SCENARIO 1: The "Before" Case (No Caching) ---
    console.log("\\n[SCENARIO 1: Before Refactor - No Caching]");
    // Every time this function is called, it will perform the same series
    // of slow, expensive database lookups.
    getDashboardSummaryBefore(USER_ID);


    // --- SCENARIO 2: The "After" Case (With Caching) ---
    console.log("\\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]");
    
    // First call for a user is a "cache miss". The app has to do the slow
    // work of hitting the database. This call will be slow.
    console.log("\\n(First call for a new user... expect a cache miss)");
    getDashboardSummaryAfter(USER_ID);

    // The user refreshes the page. The data is now in the cache.
    // This second call is a "cache hit" and will be dramatically faster
    // because it completely avoids the slow database calls.
    console.log("\\n(Second call for the same user... expect a cache hit)");
    getDashboardSummaryAfter(USER_ID);
    console.log("--------------------------------------\\n");
}

runPerformanceDemo();