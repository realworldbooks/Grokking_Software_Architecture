/**
 * THE ANEMIC DOMAIN MODEL.
 * Just a property holder with no internal rules #A, #B.
 */
class Order {
    constructor() {
        this.id = null;
        this.total = 0;
        this.customerEmail = "";
        this.items = [];
    }
}

module.exports = Order;