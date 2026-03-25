const { 
    IOrderRepository, 
    ICustomerRepository, 
    IItemRepository, 
    IEmailService 
} = require('../domain/interfaces/dataAccessInterfaces');
const Item = require('../domain/models/item');
const Customer = require('../domain/models/customer');
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
class SqlOrderRepository extends IOrderRepository {
    save(order) {
        console.log(`  [DB] SQL: Saving Order ${order.id} with Total ${order.totalPrice.toFixed(2)}`);
    }
}

class SqlCustomerRepository extends ICustomerRepository {
    getById(customerId) {
        console.log(`  [DB] SQL: Fetching Customer ${customerId}`);
        // Returning a dummy Gold customer to trigger the Rich Domain logic
        return new Customer(customerId, "Gold", "gold@example.com");
    }
}

/**
 * INFRASTRUCTURE LAYER: SQL IMPLEMENTATION
 * Simulates a database lookup to ensure we get the official, secure price.
 * * @implements {IItemRepository}
 */
class SqlItemRepository extends IItemRepository {
    getById(itemId) {
        console.log(`  [DB] Fetching official price for Item ID: ${itemId} from SQL.`);

        // Simulated database lookup
        if (itemId === 1) {
            return new Item(1, 100.0, 0);
        } else if (itemId === 2) {
            return new Item(2, 50.0, 0);
        }
        return null;
    }
}

module.exports = { 
    SqlOrderRepository, 
    SqlCustomerRepository, 
    SqlItemRepository, 
    SmtpEmailService 
};