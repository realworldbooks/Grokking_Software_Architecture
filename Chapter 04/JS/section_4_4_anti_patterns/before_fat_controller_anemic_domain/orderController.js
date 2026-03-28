const { Order, MyDbContext, SmtpEmailService } = require('./models');

/// ARCHITECTURAL NOTE: The Fat Controller Anti-Pattern.
/// This controller produces the EXACT SAME output as a clean architecture, 
/// but it does so by violating the Single Responsibility Principle. It mixes 
/// HTTP orchestration, data access, and business logic into a single file.

const createOrder = (req, res) => {
    const request = req.body;

    // 1. Validation Logic
    if (!request.items || request.items.length === 0) {
        return res.status(400).send("Order must have items.");
    }

    // 2. Infrastructure Coupling (Using 'new')
    // By directly instantiating MyDbContext, we cannot swap out the database 
    // for testing or future migrations.
    const dbContext = new MyDbContext();

    // Messy Inline Lookup (Controller acting as a repository)
    const customer = dbContext.customers.find(c => c.id === request.customerId);
    if (!customer) {
        return res.status(400).send("Customer not found.");
    }

    // 3. Core Business Logic & N+1 Query Problem
    let total = 0;
    for (const reqItem of request.items) {
        // We are querying the database inside a loop, which destroys performance.
        const dbItem = dbContext.items.find(i => i.id === reqItem.itemId);
        if (!dbItem) {
            return res.status(400).send(`Item ${reqItem.itemId} not found.`);
        }
        total += dbItem.price * reqItem.quantity;
    }

    // 4. Hardcoded Business Rules (Applying Discount)
    if (customer.type === "Gold") {
        total *= 0.9; // 10% discount #A
    }

    // 5. Anemic Model Usage & Persistence
    const order = new Order();
    order.id = Math.floor(Math.random() * 9000) + 1000;
    order.total = total;
    order.customerEmail = customer.email;

    dbContext.orders.push(order);
    dbContext.saveChanges();

    // 6. External Service Logic (Hidden Side Effects)
    const emailService = new SmtpEmailService();
    emailService.send(order.customerEmail, "Order Confirmed!");

    return res.status(200).json({
        orderId: order.id,
        totalPrice: order.total,
        customerEmail: order.customerEmail
    });
};

module.exports = { createOrder };