/**
 * A Simple Data Transfer Object (DTO).
 * * ARCHITECTURE NOTE: This class is a pure data container. The problem 
 * isn't the data itself, but rather that the logic for every single 
 * part of the business process is currently being "shovelled" into 
 * a single processor class.
 */
class Order {
    constructor(items, total, customerEmail) {
        this.items = items;
        this.total = total;
        this.customerEmail = customerEmail;
    }
}

module.exports = Order;