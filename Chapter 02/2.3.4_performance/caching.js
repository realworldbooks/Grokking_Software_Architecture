// --- "Before" Code: The "Brute Force" Query ---

// Simulating a slow database service
const db_before = {
  getProfile: (userId) => { console.log("(Before) Getting profile..."); return { id: userId, name: "Archie" }; },
  getOrders: (userId) => { console.log("(Before) Getting orders..."); return [{ id: 1, total: 50 }]; },
  getActivity: (userId) => { console.log("(Before) Getting activity..."); return [{ log: "Logged in" }]; }
};

function getDashboardSummaryBefore(userId) {
  console.log("\n--- Calling BEFORE function ---");
  const profile = db_before.getProfile(userId);
  const orders = db_before.getOrders(userId);
  const activity = db_before.getActivity(userId);
  
  return { profile, orders, activity };
}

// --- "After" Code: The "Smart Cache" Architecture ---

// Simulating a fast cache and the same slow db
const cache = {
  _store: {},
  get: (key) => { 
    console.log(`(After) Checking cache for: ${key}`);
    return cache._store[key] || null;
  },
  set: (key, value, ttl) => {
    console.log(`(After) Storing in cache: ${key}`);
    cache._store[key] = value;
    // In a real cache, ttl would set an expiration
  }
};

const db_after = {
  getProfile: (userId) => { console.log("(After) SLOW QUERY: Getting profile..."); return { id: userId, name: "Archie" }; },
  getOrders: (userId) => { console.log("(After) SLOW QUERY: Getting orders..."); return [{ id: 1, total: 50 }]; },
  getActivity: (userId) => { console.log("(After) SLOW QUERY: Getting activity..."); return [{ log: "Logged in" }]; }
};

function getDashboardSummaryAfter(userId) {
  console.log(`\n--- Calling AFTER function (User ${userId}) ---`);
  const cacheKey = `dashboard:${userId}`;
  
  // 1. Check the FAST cache first
  const cachedDashboard = cache.get(cacheKey);
  
  if (cachedDashboard) {
    console.log("(After) Cache HIT!");
    return cachedDashboard;
  }
  
  console.log("(After) Cache MISS!");
  // 2. Cache MISS. Do the slow work...
  const profile = db_after.getProfile(userId);
  const orders = db_after.getOrders(userId);
  const activity = db_after.getActivity(userId);
  
  const dashboardData = { profile, orders, activity };
  
  // 3. ...and save the result in the cache for next time.
  cache.set(cacheKey, dashboardData, 600);
  
  return dashboardData;
}

// --- runnable section ---
console.log("--- Running Performance Example ---");

// "Before" version always runs 3 slow queries
getDashboardSummaryBefore(1);
getDashboardSummaryBefore(1);

// "After" version is slow the first time, fast the second time
getDashboardSummaryAfter(2);
getDashboardSummaryAfter(2); // This call will be fast

console.log("-----------------------------------");