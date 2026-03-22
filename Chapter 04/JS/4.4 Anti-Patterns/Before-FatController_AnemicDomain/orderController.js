// Local files must start with './' to tell Node to look in 
// the current directory rather than node_modules.
const Order = require('./order');
const MyDbContext = require('./myDbContext');
const SmtpEmailService = require('./smtpEmailService');

/**
 * ANTI-PATTERN: THE FAT CONTROLLER.
 * Business logic is "leaking" into the transport layer.
 */
class OrderController {
    async createOrder(req, res) {
        const { items, customerType, customerEmail } = req.body;

        // 1. Validation Logic
        if (!items || items.length === 0) {
            return res.status(400).send("Order must have items.");
        }

        // 2 & 3. Business Logic (Calculation/Discount)
        let total = items.reduce((sum, i) => sum + i.price * i.qty, 0);
        if (customerType === "Gold") {
            total *= 0.9;
        }

        // 4. Data Access Logic
        const db = new MyDbContext();
        const order = new Order();
        order.total = total;
        await db.save(order);

        // 5. External Service Logic
        const mail = new SmtpEmailService();
        mail.send(customerEmail, "Order Confirmed!");

        res.status(200).json({ id: order.id });
    }
}