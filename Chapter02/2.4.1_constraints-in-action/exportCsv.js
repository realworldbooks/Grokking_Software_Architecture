const express = require('express');
const app = express();
const port = 3000;

// --- FAKE DATABASE ---
// This simulates our database call
const db = {
  fetchUserData: async (userId) => {
    console.log(`Fetching data for user ${userId}...`);
    // Simulate a database delay
    await new Promise(resolve => setTimeout(resolve, 100)); 
    
    // Return our fake user data
    if (userId === 123) {
      return { id: 123, name: "Archie Gumdrop", email: "archie@example.com" };
    }
    return null;
  }
};

// --- FAKE AUTHENTICATION ---
// A simple middleware to simulate a logged-in user
const fakeAuth = (req, res, next) => {
  req.user = { id: 123 }; // Hardcode user ID 123
  next();
};

// --- The "Optimal" Solution (The Reality) ---
// We apply our fake auth middleware to all routes
app.use(fakeAuth);

app.get('/export-user-data', async (req, res) => {
  console.log("Hit /export-user-data endpoint...");
  try {
    const userId = req.user.id;
    const userData = await db.fetchUserData(userId); // Assume this gets the data

    if (!userData) {
      return res.status(404).send('User not found.');
    }

    // Simple CSV conversion
    const headers = 'id,name,email\n';
    const csvRow = `${userData.id},${userData.name},${userData.email}\n`;
    const csvData = headers + csvRow;

    // Set headers to trigger a file download
    res.setHeader('Content-Type', 'text/csv');
    res.setHeader('Content-Disposition', `attachment; filename="user_data_${userId}.csv"`);
    
    console.log("Successfully generated CSV, sending file...");
    res.status(200).end(csvData);

  } catch (error) {
    console.error('Export failed:', error);
    res.status(500).send('An error occurred during export.');
  }
});

// --- Server Startup ---
app.listen(port, () => {
  console.log(`"Good Enough for Now" CSV server running at http://localhost:${port}`);
  console.log(`Test by visiting: http://localhost:${port}/export-user-data`);
});