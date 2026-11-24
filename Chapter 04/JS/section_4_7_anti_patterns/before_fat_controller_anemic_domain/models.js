/// ARCHITECTURAL NOTE: The Anemic Domain Model.
/// This class is merely a "data bag." It violates encapsulation because 
/// JS allows external functions to mutate these properties directly.
class Order {
    constructor() {
        this.id = 0;
        this.total = 0.0;
        this.customerEmail = "";
    }
}

/// ARCHITECTURAL NOTE: The Mock Database Context.
/// This mimics a direct ORM context (like Sequelize or Prisma), demonstrating 
/// how persistence logic is often leaked directly into the Express route.
class MyDbContext {
    constructor() {
        this.customers = [
            { id: 1, type: "Gold", email: "a@b.com" }
        ];
        this.items = [
            { id: 1, name: "Laptop", price: 100.0 },
            { id: 2, name: "Mouse", price: 50.0 }
        ];
        this.orders = [];
    }

    saveChanges() {
        // Simulates committing a transaction to the DB
    }
}

class SmtpEmailService {
    send(email, message) {
        console.log(`Email sent to ${email}`);
    }
}

module.exports = { Order, MyDbContext, SmtpEmailService };