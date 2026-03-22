// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/data_access/Repositories.js
const { IOrderRepository } = require("./DataAccessInterfaces");

/**
 * Simulates saving an order to a SQL database.
 * @implements {IOrderRepository}
 */
class OrderRepository extends IOrderRepository {
    saveOrder(order) {
        // In a real implementation, you would connect to a DB
        // and execute SQL statements here.
        console.log("---");
        console.log("Executing SQL: INSERT INTO Orders (CustomerId, TotalPrice)");
        console.log(`VALUES (${order.getCustomer().id}, ${order.totalPrice})`);
        
        for (const itemDetails of order.getItems()) {
            const item = itemDetails.item;
            const quantity = itemDetails.quantity;
            const price = itemDetails.price;
            console.log("Executing SQL: INSERT INTO OrderItems (OrderId, ItemId, Quantity, Price)");
            console.log(`VALUES (LAST_INSERT_ID(), ${item.id}, ${quantity}, ${price})`);
        }
        
        console.log("Order saved to database.");
        console.log("---");
    }
}

module.exports = { OrderRepository };
