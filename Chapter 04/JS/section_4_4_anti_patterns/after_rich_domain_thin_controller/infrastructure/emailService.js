const { IEmailService } = require("../domain/interfaces/dataAccessInterfaces");

/**
 * Simulates sending an order confirmation email via SMTP.
 * @implements {IEmailService}
 */
class SmtpEmailService extends IEmailService {
    sendOrderConfirmation(order) {
        const customerName = order.getCustomer().name;
        const totalPrice = order.totalPrice;

        // In a real system, you would connect to an SMTP server
        // and send a formatted email.
        console.log("---");
        console.log("Connecting to SMTP server...");
        console.log(`Sending email to ${customerName}:`);
        console.log("Subject: Your Order Confirmation");
        console.log(`Body: Thank you for your order of $${totalPrice.toFixed(2)}.`);
        console.log("Email sent.");
        console.log("---");
    }
}

module.exports = { SmtpEmailService };
