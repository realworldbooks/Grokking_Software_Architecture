const db = require('./database');

// We simulate Express req/res objects so the core logic stays 1:1 with the book
async function exportUserDataRoute(req, res) {
    try {
        const userId = req.user.id;
        const userData = await db.fetchUserData(userId);

        if (!userData) {
            return res.status(404).send('User not found.');
        }

        // Simple CSV conversion
        const headers = 'id,name,email\n';
        const csvRow = `${userData.id},${userData.name},${userData.email}\n`;
        const csvData = headers + csvRow;

        res.setHeader('Content-Type', 'text/csv');
        res.setHeader(
            'Content-Disposition', 
            `attachment; filename="user_data_${userId}.csv"`
        );
        res.status(200).end(csvData);

    } catch (error) {
        console.error('Export failed:', error);
        res.status(500).send('An error occurred during export.');
    }
}

module.exports = exportUserDataRoute;