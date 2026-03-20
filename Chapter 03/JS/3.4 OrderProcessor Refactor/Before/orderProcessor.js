class OrderProcessor {
    process(order) {
        console.log("  [Validate] Validating order...");
        if (order.items.length === 0 || order.total <= 0) {
            throw new Error("Order is invalid.");
        }

        console.log(`  [Payment] Processing payment for $${order.total.toFixed(2)}...`);
        const paymentSuccess = true;

        if (paymentSuccess) {
            console.log("  [Inventory] Updating inventory...");
            console.log(`  [Notify] Sending confirmation email to ${order.customerEmail}...`);
            return "Order processed successfully.";
        } else {
            return "Payment failed.";
        }
    }
}
module.exports = OrderProcessor;