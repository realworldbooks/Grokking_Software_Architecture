/**
 * ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
 * Because the core business rules for this bounded context revolve 
 * around the Order, this Customer class can remain a simple data 
 * entity holding state.
 */
class Customer {
    constructor(id = 0, type = "", email = "") {
        this.id = id;
        this.type = type;
        this.email = email;
    }
}
module.exports = Customer;