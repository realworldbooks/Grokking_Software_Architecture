/**
 * ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
 * how items are priced and discounted is encapsulated inside the 
 * Rich 'Order' model, not here.
 */
class Item {
    constructor() {
        this.price = 0.0;
        this.quantity = 0;
    }
}

module.exports = Item;