const { IEmailService } = require('./dataAccessInterfaces');

/**
 * ARCHITECTURE NOTE: By isolating Email logic here, we prevent 
 * database concerns from "leaking" into the Presentation or 
 * Business layers.
 * * @implements {IEmailService}
 */
class SmtpEmailService extends IEmailService {
    // Concrete implementation for an email provider
    send(to, subject, body) {
        // Implementation logic would go here
    }
}

/**
 * DATA ACCESS LAYER: SQL IMPLEMENTATION
 * Simulates a database lookup to ensure we get the official, secure price.
 */
class SqlItemRepository {
    getById(itemId) {
        console.log(`  [DB] Fetching official data for Item ID: ${itemId} from SQL.`);
        
        const item = new Item();
        if (itemId === 1) {
            item.price = 100.0;
        } else if (itemId === 2) {
            item.price = 50.0;
        } else {
            item.price = 75.0; // Fallback
        }
        return item;
    }
}

module.exports = { SmtpEmailService, SqlItemRepository };