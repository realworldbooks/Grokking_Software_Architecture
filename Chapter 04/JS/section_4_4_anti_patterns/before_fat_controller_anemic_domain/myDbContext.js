/**
 * INFRASTRUCTURE LAYER: DATABASE.
 * ARCHITECTURE NOTE: This class simulates a direct 
 * connection to a database.
 */
class MyDbContext {
    async save(order) {
        console.log(`  [DB] Persistence: Saving Order ${order.id}`);
    }
}

module.exports = MyDbContext;