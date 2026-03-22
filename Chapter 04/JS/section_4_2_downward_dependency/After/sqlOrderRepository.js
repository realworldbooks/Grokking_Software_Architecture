/**
 * DATA ACCESS LAYER.
 * ARCHITECTURE NOTE: In JS, this class implicitly satisfies the 
 * "Repository Contract" by implementing the save() method.
 */
class SqlOrderRepository {
    save(order) {
        console.log("(After Refactor) Saving order to SQL...");
    }
}

module.exports = SqlOrderRepository;